package com.alura.challenge_literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double numeroDeDescargas;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor;

    public Libro() {
    }

    public Libro(DatosLibro datos) {
        this.titulo = datos.titulo();
        this.idioma = datos.idiomas().get(0);
        this.numeroDeDescargas = datos.numeroDeDescargas();
        List<Autor> autorEncontrado = datos.autor().stream()
                .map(a -> new Autor(a))
                .collect(Collectors.toList());
        autorEncontrado.forEach(a -> a.setLibro(this));
        this.autor = autorEncontrado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setIdiomas(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n-----------------------------------------" + "\n" +
                "ID: " + id + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " +
                autor.stream().
                        map(Autor::getNombre)
                        .collect(Collectors.joining("; ", "[","]")) + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de descargas: " + numeroDeDescargas + "\n" +
                "-----------------------------------------\n";
    }
}
