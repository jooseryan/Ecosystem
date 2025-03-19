package br.com.ecosystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "biblioteca")
public class Biblioteca{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Column(length = 1000)
    private String titulo;

    @Column(length = 1000)
    private String autor;

    private int ano;

    @Column(length = 1000)
    private String referencia;

    @Column(length = 1000)
    private String link;

    private String tipo;

    private String midia;

    @Column(length = 1000)
    private String linkDrive;

    @Column(length = 1000)
    private String imagem;

    @Column(length = 1000)
    private String observacoes;

    public Biblioteca() {}

    public Biblioteca(String codigo, String titulo, String autor, int ano, String referencia, String link, String tipo, String midia, String linkDrive, String imagem, String observacoes) {
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

    public Biblioteca(BibliotecaBuilder bibliotecaBuilder) {
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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

    public static class BibliotecaBuilder {
        private int ano;
        private String autor;
        private String codigo;
        private String link;
        private String linkDrive;
        private String midia;
        private String referencia;
        private String tipo;
        private String titulo;
        private String observacoes;

        public BibliotecaBuilder() {}

        public BibliotecaBuilder ano(int ano) {
            this.ano = ano;
            return this;
        }

        public BibliotecaBuilder autor(String autor) {
            this.autor = autor;
            return this;
        }

        public BibliotecaBuilder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public BibliotecaBuilder link(String link) {
            this.link = link;
            return this;
        }

        public BibliotecaBuilder linkDrive(String linkDrive) {
            this.linkDrive = linkDrive;
            return this;
        }

        public BibliotecaBuilder midia(String midia) {
            this.midia = midia;
            return this;
        }

        public BibliotecaBuilder referencia(String referencia) {
            this.referencia = referencia;
            return this;
        }

        public BibliotecaBuilder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public BibliotecaBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public BibliotecaBuilder observacoes(String observacoes){
            this.observacoes = observacoes;
            return this;
        }

        public Biblioteca build() {
            return new Biblioteca(this);
        }
    }
}