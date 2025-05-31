package br.ufpb.ecosystem.dtos;

public class AuthorDTO {

    private Long id;
    private String name;
    private String email;
    private String orcid;
    private String affiliation;

    public AuthorDTO() {}

    public AuthorDTO(Long id, String name, String email, String orcid, String affiliation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.orcid = orcid;
        this.affiliation = affiliation;
    }

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
}
