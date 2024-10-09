package com.example.PetScan.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ResultValueDTO(

        double value,

        @NotNull(message = "L'id del valore e' obbligatorio!")
        UUID valuesNameId,

        String valueName,
        Double referenceValue,
        List<String> unit,
        String pathologicalCondition

) {
}
