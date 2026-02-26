package com.example.demo.model;


import jakarta.persistence.*;

@Entity
@Table(name = "autores")

public class AutorBaseDatos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(unique = true)

    private String nombre;
    private Integer anoNacimiento;

    public AutorBaseDatos(){}

    public AutorBaseDatos(String nombre, Integer anoNacimiento){
        this.nombre = nombre;
        this.anoNacimiento = anoNacimiento;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }



}
