package br.ufpb.ecosystem.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String orcid;

    private String affiliation;

    @ManyToMany(mappedBy = "authors")
    private List<BibliographicSource> bibliographicSources = new ArrayList<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public List<BibliographicSource> getBibliographicSources() {
        return bibliographicSources;
    }

    public void setBibliographicSources(List<BibliographicSource> bibliographicSources) {
        this.bibliographicSources = bibliographicSources;
    }
}
