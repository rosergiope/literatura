package com.alura.challenge_literatura;

import com.alura.challenge_literatura.principal.Principal;
import com.alura.challenge_literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository repositorio;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeLiteraturaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositorio);
        principal.muestraElMenu();
    }
}
