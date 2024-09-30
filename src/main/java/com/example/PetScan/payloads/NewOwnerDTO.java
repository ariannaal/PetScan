package com.example.PetScan.payloads;

import com.example.PetScan.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewOwnerDTO(

        @NotEmpty(message = "Il nome e' obbligatorio!")
        @Size(min = 5, max = 15, message = "Lo nome deve essere compreso tra 5 e 15 caratteri")
        String name,

        @NotEmpty(message = "Il cognome e' obbligatorio!")
        @Size(min = 5, max = 15, message = "Il cognome deve essere compreso tra 5 e 15 caratteri")
        String surname,

        @NotNull(message = "Il genere e' obbligatorio!")
        Gender gender,

        @NotNull(message = "La data di nascita e' obbligatoria!")
        LocalDate dateOfBirth,

        @NotEmpty(message = "L'email e' obbligatoria!")
        @Email(message = "L'email deve essere valida!")
        String email,

        @NotEmpty(message = "La password e' obbligatoria!")
        @Size(min = 5, max = 15, message = "La password deve essere compresa tra 5 e 15 caratteri")
        String password

) {
}
