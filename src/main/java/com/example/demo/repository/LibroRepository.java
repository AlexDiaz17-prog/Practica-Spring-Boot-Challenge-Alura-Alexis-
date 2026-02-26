package com.example.demo.repository;

import com.example.demo.model.LibroBaseDatos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<LibroBaseDatos, Long> {

    Optional<LibroBaseDatos> findByTitulo(String titulo);

    List<LibroBaseDatos> findByIdioma(String idioma);
}
