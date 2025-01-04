package com.alura.challenge_literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double numeroDeDescargas
) {
    @Override
    public String toString() {
        return "\n-----------------------------------------" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + autor.get(0).nombre() + "\n" +
                "Idioma: " + idiomas.get(0) + "\n" +
                "Número de descargas: " + numeroDeDescargas + "\n" +
                "-----------------------------------------\n";
    }
}
