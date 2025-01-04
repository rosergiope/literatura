package com.alura.challenge_literatura.repository;

import com.alura.challenge_literatura.model.Autor;
import com.alura.challenge_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT a FROM Libro l JOIN l.autor a WHERE :fecha >= a.fechaDeNacimiento AND :fecha <= a.fechaDeFallecimiento")
    List<Autor> autoresPorFecha(int fecha);

    @Query("SELECT a FROM Libro l JOIN l.autor a ORDER BY a.nombre")
    List<Autor> autores();

    List<Libro> findByIdioma(String es);
}
