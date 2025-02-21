package br.com.ecosystem.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BibliotecaDto {

    private int ano;
    private String autor;
    private String codigo;
    private String link;
    private String linkDrive;
    private String midia;
    private String referencia;
    private String tipo;
    private String titulo;

}
