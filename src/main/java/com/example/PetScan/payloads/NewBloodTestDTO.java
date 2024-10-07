package com.example.PetScan.payloads;

import com.example.PetScan.enums.Gender;
import com.example.PetScan.enums.PetType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record NewBloodTestDTO(

        @NotNull(message = "La data dell'esame e' obbligatoria!")
        LocalDate dateOfTest,

        @NotNull(message = "Il tipo di animale e' obbligatorio!")
        PetType petType,

        @NotNull(message = "Il numero di test e' obbligatorio!")
        int testNumber,

        @NotNull(message = "L'id del proprietario e' obbligatorio!")
        UUID ownerId,

        @NotNull(message = "L'id dell'animale e' obbligatorio!")
        UUID petId

) {

}
