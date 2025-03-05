package br.com.ecosystem.dtos;

public class BibliotecaDto {
    private String codigo;
    private String titulo;
    private String autor;
    private int ano;
    private String referencia;
    private String link;
    private String tipo;
    private String midia;
    private String linkDrive;
    private String imagem;
    private String observacoes;

    public BibliotecaDto() {}

    public BibliotecaDto(String codigo, String titulo, String autor, int ano, String referencia, String link, String tipo, String midia, String linkDrive, String imagem, String observacoes) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.referencia = referencia;
        this.link = link;
        this.tipo = tipo;
        this.midia = midia;
        this.linkDrive = linkDrive;
        this.imagem = imagem;
        this.observacoes = observacoes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
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

    public String getMidia() {
        return midia;
    }

    public void setMidia(String midia) {
        this.midia = midia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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
}
