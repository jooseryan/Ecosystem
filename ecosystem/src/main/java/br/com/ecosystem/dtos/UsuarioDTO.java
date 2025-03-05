package br.com.ecosystem.dtos;

import br.com.ecosystem.models.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UsuarioDTO {

    @NotBlank(message = "O nome de usuário não pode estar vazio.")
    @Size(min = 4, max = 50, message = "O nome de usuário deve ter entre 4 e 50 caracteres.")
    private String username;

    @NotBlank(message = "A senha não pode estar vazia.")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Set<String> role;


    public UsuarioDTO() {}

    public UsuarioDTO(String username, String password, Set<String> role) {
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
