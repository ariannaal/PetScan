package com.example.PetScan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permette a tutti i percorsi
                .allowedOrigins("http://localhost:5173","https://petscan.netlify.app") // Consenti solo questo dominio
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Metodi consentiti
                .allowCredentials(true); // Consenti le credenziali

    }
}

