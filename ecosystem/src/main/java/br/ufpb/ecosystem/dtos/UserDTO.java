package br.ufpb.ecosystem.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserDTO {

    @NotBlank(message = "Username must not be blank.")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters long.")
    private String username;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters long.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Set<String> role;

    public UserDTO() {}

    public UserDTO(String username, String password, Set<String> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
