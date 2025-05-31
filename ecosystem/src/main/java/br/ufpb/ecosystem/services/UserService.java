package br.ufpb.ecosystem.services;

import br.ufpb.ecosystem.dtos.UserDTO;
import br.ufpb.ecosystem.entities.User;
import br.ufpb.ecosystem.entities.UserRole;
import br.ufpb.ecosystem.repositories.RoleRepository;
import br.ufpb.ecosystem.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

        createDefaultRoleIfNotExists();
    }

    /**
     * Ensures the default role exists (e.g., ROLE_USER).
     */
    private void createDefaultRoleIfNotExists() {
        roleRepository.findByRole("ROLE_USER").orElseGet(() -> {
            UserRole defaultRole = new UserRole();
            defaultRole.setRole("ROLE_USER");
            return roleRepository.save(defaultRole);
        });
    }

    /**
     * Registers a new user.
     */
    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists.");
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
            Set<UserRole> roles = userDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleStr)))
                    .collect(Collectors.toSet());
            newUser.setRoles(roles);
        } else {
            UserRole defaultRole = roleRepository.findByRole("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role 'ROLE_USER' not found!"));
            newUser.setRoles(Collections.singleton(defaultRole));
        }

        return userRepository.save(newUser);
    }

    /**
     * Authenticates a user by username and password.
     */
    public User loginUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials.");
        }

        return user;
    }

    /**
     * Required by Spring Security to load user by username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList() // Authorities should be added here if needed
        );
    }

    /**
     * Lists all users.
     */
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Fetches a user by their ID.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    /**
     * Fully updates an existing user.
     */
    public User updateUser(Long id, UserDTO userDTO) {
        User existingUser = getUserById(id);
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
            Set<UserRole> updatedRoles = userDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + roleStr)))
                    .collect(Collectors.toSet());
            existingUser.setRoles(updatedRoles);
        }

        return userRepository.save(existingUser);
    }

    /**
     * Partially updates a user (PATCH).
     */
    public User updateUserPartially(Long id, UserDTO userDTO) {
        User existingUser = getUserById(id);

        if (userDTO.getUsername() != null && !userDTO.getUsername().isBlank()) {
            existingUser.setUsername(userDTO.getUsername());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
            Set<UserRole> updatedRoles = userDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + roleStr)))
                    .collect(Collectors.toSet());
            existingUser.setRoles(updatedRoles);
        }

        return userRepository.save(existingUser);
    }

    /**
     * Deletes a user by ID.
     */
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
