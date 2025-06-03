package br.ufpb.ecosystem.dtos;

import br.ufpb.ecosystem.entities.BibliographicSource;

import java.util.List;

public class KeywordDTO {

    private Long id;
    private String value;

    public KeywordDTO() {}

    public KeywordDTO(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
