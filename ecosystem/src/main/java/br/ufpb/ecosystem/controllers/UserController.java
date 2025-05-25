package br.ufpb.ecosystem.controllers;

import br.ufpb.ecosystem.dtos.ErrorResponseDTO;
import br.ufpb.ecosystem.dtos.LoginResponseDTO;
import br.ufpb.ecosystem.dtos.UserDTO;
import br.ufpb.ecosystem.entities.User;
import br.ufpb.ecosystem.security.SecurityConfig;
import br.ufpb.ecosystem.security.TokenService;
import br.ufpb.ecosystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuário", description = "Controlador responsável por operações de autenticação e gerenciamento de usuários.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UserController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Autenticação de usuário",
            description = "Realiza a autenticação de um usuário com base nas credenciais fornecidas e retorna um token JWT em caso de sucesso."
    )
    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas ou usuário não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );

        User user = userService.realizarLogin(userDTO);
        String token = tokenService.generateToken(user);

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } else {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO("Usuário ou senha inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/register")
    @Operation(
            summary = "Cadastro de novo usuário",
            description = "Cadastra um novo usuário no sistema com base nas informações fornecidas."
    )
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação ou usuário já existente")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<?> registrarUsuario(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.cadastrarUsuario(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista com todos os usuários cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuários obtida com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<List<User>> listarTodosUsuarios() {
        return ResponseEntity.ok(userService.listarTodosUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<User> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.buscarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar dados completos do usuário",
            description = "Atualiza todos os dados de um usuário específico com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<User> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.atualizarUsuario(id, userDTO));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualização parcial de dados do usuário",
            description = "Atualiza parcialmente os dados de um usuário com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado parcialmente com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<User> atualizarUsuarioParcial(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.atualizarUsuarioParcial(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        try {
            userService.deletarUsuario(id);
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
}
