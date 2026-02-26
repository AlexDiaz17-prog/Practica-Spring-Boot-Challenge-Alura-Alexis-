package com.example.demo.repository;

import com.example.demo.model.AutorBaseDatos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorBaseDatos, Long> {
    Optional<AutorBaseDatos> findByNombre(String nombre);

    List<AutorBaseDatos>
    findByAnoNacimientoLessThanEqualAndAnoNacimientoIsNotNull(Integer ano);

}

