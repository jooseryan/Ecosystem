package br.ufpb.ecosystem.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "FonteBibliografica")
public class BibliographicSource {

    private static final int TAMANHO_TEXTO_DEFAULT = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoDoRevisor;

    @Column(length = TAMANHO_TEXTO_DEFAULT)
    private String titulo;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "fonte_autor",
            joinColumns = @JoinColumn(name = "fonte_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Author> autores;

    private int ano;

    @Column(length = TAMANHO_TEXTO_DEFAULT)
    private String referencia;

    @Column(length = TAMANHO_TEXTO_DEFAULT)
    private String link;

    private String tipo;

    private String midia;

    @Column(length = TAMANHO_TEXTO_DEFAULT)
    private String linkDrive;

    @Column(length = TAMANHO_TEXTO_DEFAULT)
    private String linkImagem;

    @Column(length = TAMANHO_TEXTO_DEFAULT)
    private String observacoes;

    @ElementCollection
    @Column(name = "palavra_chave")
    private List<String> palavraChave;

    private String resumo;

    public BibliographicSource() {
        // TO DO this("", "", new ArrayList<>(), 0, )
    }

    public BibliographicSource(String codigo, String titulo, List<Author> autores, int ano, String referencia, String link,
                               String tipo, String midia, String linkDrive, String linkImagem, String observacoes,
                               List<String> palavrasChaves, String resumo) {
        this.codigoDoRevisor = codigo;
        this.titulo = titulo;
        this.autores = autores;
        this.ano = ano;
        this.referencia = referencia;
        this.link = link;
        this.tipo = tipo;
        this.midia = midia;
        this.linkDrive = linkDrive;
        this.linkImagem = linkImagem;
        this.observacoes = observacoes;
        this.palavraChave = palavrasChaves;
        this.resumo = resumo;
    }

    public Long getId() {
        return id;
    }

    public String getCodigoDoRevisor() {
        return codigoDoRevisor;
    }

    public void setCodigoDoRevisor(String codigoDoRevisor) {
        this.codigoDoRevisor = codigoDoRevisor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Author> getAutores() {
        return autores;
    }

    public void setAutores(List<Author> autores) {
        this.autores = autores;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagem() {
        return linkImagem;
    }

    public void setImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public String getMidia() {
        return midia;
    }

    public void setMidia(String midia) {
        this.midia = midia;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getLinkDrive() {
        return linkDrive;
    }

    public void setLinkDrive(String linkDrive) {
        this.linkDrive = linkDrive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public List<String> getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(List<String> palavraChave) {
        this.palavraChave = palavraChave;
    }

}