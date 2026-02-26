package com.example.demo.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class LibroBaseDatos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)


    private String titulo;

    private String idioma;

    private Integer descargas;

    @ManyToMany
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<AutorBaseDatos> autores;

    public LibroBaseDatos() {}

    public LibroBaseDatos(String titulo, String idioma, Integer descargas, List<AutorBaseDatos> autores) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
        this.autores = autores;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public List<AutorBaseDatos> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorBaseDatos> autores) {
        this.autores = autores;
    }
}



