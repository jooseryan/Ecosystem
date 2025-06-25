package br.ufpb.ecosystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String value;

    @ManyToMany(mappedBy = "keywords")
    @JsonIgnore
    private List<BibliographicSource> bibliographicSources;


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

    public List<BibliographicSource> getBibliographicSources() {
        return bibliographicSources;
    }

    public void setBibliographicSources(List<BibliographicSource> bibliographicSources) {
        this.bibliographicSources = bibliographicSources;
    }

    // equals e hashCode com base no campo "value"
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Keyword)) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(value, keyword.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
