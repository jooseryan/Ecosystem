package br.ufpb.ecosystem.services;

import br.ufpb.ecosystem.dtos.UserDTO;
import br.ufpb.ecosystem.entities.UserRole;
import br.ufpb.ecosystem.entities.User;
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


    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

        criarRoleSeNaoExistir();
    }

    private void criarRoleSeNaoExistir() {
        roleRepository.findByRole("ROLE_USER").orElseGet(() -> {
            UserRole novaUserRole = new UserRole();
            novaUserRole.setRole("ROLE_USER");
            return roleRepository.save(novaUserRole);
        });
    }

    public User cadastrarUsuario(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Usuário já existe.");
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Verifica se as roles foram enviadas no DTO
        if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
            Set<UserRole> userRoles = userDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleStr)))
                    .collect(Collectors.toSet());
            newUser.setRoles(userRoles);
        } else {
            // Se não forem enviadas roles, atribui um papel padrão (por exemplo, ROLE_USER)
            UserRole userRoleUser = roleRepository.findByRole("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role padrão 'ROLE_USER' não encontrada!"));
            newUser.setRoles(Collections.singleton(userRoleUser));
        }

        return userRepository.save(newUser);
    }


    public User realizarLogin(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas.");
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }


    public List<User> listarTodosUsuarios() {
        return userRepository.findAll();
    }

    public User buscarUsuarioPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public User atualizarUsuario(Long id, UserDTO userDTO) {
        User usuarioExistente = buscarUsuarioPorId(id);
        usuarioExistente.setUsername(userDTO.getUsername());
        usuarioExistente.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
            Set<UserRole> novasUserRoles = userDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new IllegalArgumentException("Role inválida: " + roleStr)))
                    .collect(Collectors.toSet());
            usuarioExistente.setRoles(novasUserRoles);
        }
        return userRepository.save(usuarioExistente);
    }

    public User atualizarUsuarioParcial(Long id, UserDTO userDTO) {
        User usuarioExistente = buscarUsuarioPorId(id);

        if (userDTO.getUsername() != null && !userDTO.getUsername().isBlank()) {
            usuarioExistente.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            usuarioExistente.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
            Set<UserRole> novasUserRoles = userDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new IllegalArgumentException("Role inválida: " + roleStr)))
                    .collect(Collectors.toSet());
            usuarioExistente.setRoles(novasUserRoles);
        }
        return userRepository.save(usuarioExistente);
    }

    public void deletarUsuario(Long id) {
        User user = buscarUsuarioPorId(id);
        userRepository.delete(user);
    }
}
