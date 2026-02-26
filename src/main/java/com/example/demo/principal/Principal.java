package com.example.demo.principal;

import com.example.demo.model.*;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.LibroRepository;
import org.springframework.stereotype.Component;
import service.ConsumoApi;
import service.ConvierteDatosAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository,
                     LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatosAPI conversor = new ConvierteDatosAPI();

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    // ================= MENU =================

    public void muestraElMenu() {

        var opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """);

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosPorAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    // ================= OPCION 1 =================

    private void buscarLibroPorTitulo() {

        System.out.println("Ingresa el nombre del libro:");
        var nombreLibro = teclado.nextLine();

        var json = consumoApi.obtenerDatosApi(URL_BASE + nombreLibro.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, DatosGeneralesLibro.class);

        if (datos.results() == null || datos.results().isEmpty()) {
            System.out.println("No se encontraron resultados");
            return;
        }

        var libroAPI = datos.results().get(0);

        if (libroRepository.findByTitulo(libroAPI.title()).isPresent()) {
            System.out.println("El libro ya existe en la base de datos");
            return;
        }

        List<AutorBaseDatos> autoresBD = new ArrayList<>();

        for (Autor autor : libroAPI.authors()) {

            AutorBaseDatos autorBD = autorRepository
                    .findByNombre(autor.name())
                    .orElseGet(() ->
                            autorRepository.save(
                                    new AutorBaseDatos(
                                            autor.name(),
                                            autor.birth_year()
                                    )
                            )
                    );

            autoresBD.add(autorBD);
        }

        LibroBaseDatos libroBD = new LibroBaseDatos(
                libroAPI.title(),
                libroAPI.languages().isEmpty() ? "desconocido" : libroAPI.languages().get(0),
                libroAPI.downloadCount(),
                autoresBD
        );

        libroRepository.save(libroBD);

        System.out.println("Libro guardado correctamente ✔");
    }

    // ================= OPCION 2 =================

    private void listarLibros() {

        var libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }

        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("------------------------");
        });
    }

    // ================= OPCION 3 =================

    private void listarAutores() {

        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
            return;
        }

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Año nacimiento: " + autor.getAnoNacimiento());
            System.out.println("------------------------");
        });
    }

    // ================= OPCION 4 =================

    private void listarAutoresVivosPorAnio() {

        System.out.println("Ingrese el año:");
        var anio = teclado.nextInt();
        teclado.nextLine();

        var autores = autorRepository.findAll();

        var autoresVivos = autores.stream()
                .filter(a -> a.getAnoNacimiento() != null && a.getAnoNacimiento() <= anio)
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año");
            return;
        }

        autoresVivos.forEach(a ->
                System.out.println("Autor: " + a.getNombre())
        );
    }

    // ================= OPCION 5 =================

    private void listarLibrosPorIdioma() {

        System.out.println("""
                Ingrese idioma:
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """);

        var idioma = teclado.nextLine();

        var libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros en ese idioma");
            return;
        }

        libros.forEach(libro ->
                System.out.println("Título: " + libro.getTitulo())
        );
    }
}