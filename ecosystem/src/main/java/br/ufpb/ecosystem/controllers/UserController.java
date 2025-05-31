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
@RequestMapping("/users")
@Tag(name = "User", description = "Controller responsible for authentication and user management operations.")
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
            summary = "User authentication",
            description = "Authenticates a user based on the provided credentials and returns a JWT token upon success."
    )
    @ApiResponse(responseCode = "200", description = "User successfully authenticated")
    @ApiResponse(responseCode = "401", description = "Invalid credentials or user not authenticated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );

        User user = userService.loginUser(userDTO);
        String token = tokenService.generateToken(user);

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } else {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register new user",
            description = "Registers a new user in the system using the provided information."
    )
    @ApiResponse(responseCode = "201", description = "User successfully registered")
    @ApiResponse(responseCode = "400", description = "Validation error or user already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.registerUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully created!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(
            summary = "List all users",
            description = "Returns a list of all users registered in the system."
    )
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.listAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find user by ID",
            description = "Returns user details based on the provided ID."
    )
    @ApiResponse(responseCode = "200", description = "User found successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Fully update user data",
            description = "Updates all user data based on the provided ID."
    )
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Partially update user data",
            description = "Partially updates user data based on the provided ID."
    )
    @ApiResponse(responseCode = "200", description = "User partially updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<User> partialUpdateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserPartially(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user",
            description = "Removes a user from the system based on the provided ID."
    )
    @ApiResponse(responseCode = "200", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User successfully deleted!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
