package br.com.ecosystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Bibliografia")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Biblioteca {

    @Id
    private String codigo;
    private String titulo;
    private String autor;
    private int ano;
    private String tipo;
    private String midia;
    private String referencia;
    private String link;
    private String linkDrive;

}
