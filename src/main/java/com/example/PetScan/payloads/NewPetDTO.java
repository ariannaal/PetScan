package com.example.PetScan.payloads;

import com.example.PetScan.enums.Gender;
import com.example.PetScan.enums.PetType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record NewPetDTO(

        @NotEmpty(message = "Il nome dell'animale e' obbligatorio!")
        @Size(min = 2, max = 15, message = "Lo nome deve essere compreso tra 5 e 15 caratteri")
        String name,

        @NotNull(message = "Il genere e' obbligatorio!")
        PetType petType,

        @NotEmpty(message = "La razza dell'animale e' obbligatoria!")
        @Size(min = 5, max = 30, message = "La razza dell'animale deve essere compreso tra 5 e 15 caratteri")
        String breed,

        @NotNull(message = "Il genere dell'animale e' obbligatorio!")
        Gender gender,

        @NotNull(message = "L'eta' dell'animale e' obbligatorio!")
        int age,

        @NotNull(message = "La data di nascita dell'animale e' obbligatoria!")
        LocalDate dateOfBirth,

        @NotNull(message = "L'id del proprietario e' obbligatorio!")
        UUID ownerId

) {
}
