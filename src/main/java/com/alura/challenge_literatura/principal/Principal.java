package com.alura.challenge_literatura.principal;

import com.alura.challenge_literatura.model.Autor;
import com.alura.challenge_literatura.model.Datos;
import com.alura.challenge_literatura.model.DatosLibro;
import com.alura.challenge_literatura.model.Libro;
import com.alura.challenge_literatura.repository.LibroRepository;
import com.alura.challenge_literatura.service.ConsumoAPI;
import com.alura.challenge_literatura.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;

    public Principal(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosWeb();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresPorFecha();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros
                es - español
                en - inglés
                fr - francés
                pt - postugués
                """);
        var nombreIdioma = teclado.nextLine();
        List<Libro> libros = repositorio.findByIdioma(nombreIdioma);
        if (!libros.isEmpty()) {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        } else {
            System.out.println("\nNo hay libros\n");
        }
    }

    private void listarAutores() {
        List<Autor> autores = repositorio.autores();
        if (!autores.isEmpty()) {
//            autores.forEach(System.out::println);

            autores.stream()
                    .map(a -> a.toString())
                    .distinct()
                    .forEach(System.out::println);
        } else {
            System.out.println("\nNo hay autores\n");
        }
    }

    private void listarAutoresPorFecha() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar ");
        var fecha = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autores = repositorio.autoresPorFecha(fecha);
//        autores.forEach(System.out::println);

        autores.stream()
                .map(a -> a.toString())
                .distinct()
                .forEach(System.out::println);

    }

    private void listarLibros() {
        List<Libro> libros = repositorio.findAll();
        if (!libros.isEmpty()) {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        } else {
            System.out.println("\nNo hay libros\n");
        }
    }

    private void buscarLibrosWeb() {
        DatosLibro datos = getDatosLibro();

        if (datos != null) {
            Libro libro = new Libro(datos);

            try {
                repositorio.save(libro);
                System.out.println("Libro Encontrado");
                System.out.println(datos);
            } catch (DataIntegrityViolationException e) {
                System.out.println("\nNo se puede registrar el mismo libro más de una vez\n");
            }

        } else {
            System.out.println("\nLibro no encontrado\n");
        }
    }

    private DatosLibro getDatosLibro() {
        // emma, pride, quijote, twnty, romeo
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
//        System.out.println(json);
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> datos = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (datos.isPresent()) {
            return datos.get();
        }
        return null;
    }
}
