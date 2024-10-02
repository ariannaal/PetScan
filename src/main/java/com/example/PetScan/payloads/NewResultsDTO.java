package com.example.PetScan.payloads;

import com.example.PetScan.entities.Result;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record NewResultsDTO(

        @NotNull(message = "L'id dell'esame e' obbligatorio!")
        UUID bloodTestId,

        @NotEmpty(message = "La lista dei risultati non può essere vuota!")
        List<ResultValueDTO> results

) {
}
