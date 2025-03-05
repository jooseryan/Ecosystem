package br.com.ecosystem.services;

import br.com.ecosystem.dtos.UsuarioDTO;
import br.com.ecosystem.models.Role;
import br.com.ecosystem.models.Usuario;
import br.com.ecosystem.repositories.RoleRepository;
import br.com.ecosystem.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
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
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

        criarRoleSeNaoExistir("USER");
        criarRoleSeNaoExistir("ADMIN");
    }

    private void criarRoleSeNaoExistir(String role) {
        roleRepository.findByRole(role).orElseGet(() -> {
            Role novaRole = new Role();
            novaRole.setRole(role);
            return roleRepository.save(novaRole);
        });
    }

    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByUsername(usuarioDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Usuário já existe.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(usuarioDTO.getUsername());
        novoUsuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        // Verifica se as roles foram enviadas no DTO
        if (usuarioDTO.getRole() != null && !usuarioDTO.getRole().isEmpty()) {
            Set<Role> roles = usuarioDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleStr)))
                    .collect(Collectors.toSet());
            novoUsuario.setRoles(roles);
        } else {
            // Se não forem enviadas roles, atribui um papel padrão (por exemplo, ROLE_USER)
            Role roleUser = roleRepository.findByRole("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role padrão 'ROLE_USER' não encontrada!"));
            novoUsuario.setRoles(Collections.singleton(roleUser));
        }

        return usuarioRepository.save(novoUsuario);
    }


    public Usuario realizarLogin(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findByUsername(usuarioDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!passwordEncoder.matches(usuarioDTO.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciais inválidas.");
        }

        return usuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.emptyList()
        );
    }


    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public Usuario atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = buscarUsuarioPorId(id);
        usuarioExistente.setUsername(usuarioDTO.getUsername());
        usuarioExistente.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        if (usuarioDTO.getRole() != null && !usuarioDTO.getRole().isEmpty()) {
            Set<Role> novasRoles = usuarioDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new IllegalArgumentException("Role inválida: " + roleStr)))
                    .collect(Collectors.toSet());
            usuarioExistente.setRoles(novasRoles);
        }
        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario atualizarUsuarioParcial(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = buscarUsuarioPorId(id);

        if (usuarioDTO.getUsername() != null && !usuarioDTO.getUsername().isBlank()) {
            usuarioExistente.setUsername(usuarioDTO.getUsername());
        }
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isBlank()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }
        if (usuarioDTO.getRole() != null && !usuarioDTO.getRole().isEmpty()) {
            Set<Role> novasRoles = usuarioDTO.getRole().stream()
                    .map(roleStr -> roleRepository.findByRole(roleStr)
                            .orElseThrow(() -> new IllegalArgumentException("Role inválida: " + roleStr)))
                    .collect(Collectors.toSet());
            usuarioExistente.setRoles(novasRoles);
        }
        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }
}
