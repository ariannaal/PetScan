package com.example.PetScan.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ResultValueDTO(

        @NotNull(message = "Il valore e' obbligatorio!")
        Double value,

        @NotNull(message = "L'id del valore e' obbligatorio!")
        UUID valuesNameId

) {
}
