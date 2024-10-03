package com.example.PetScan.payloads;

import jakarta.validation.constraints.NotNull;

public record BloodTestAnalysisDTO(

        @NotNull(message = "Il nome del parametro non puo' essere nullo!")
        String valueName,

        @NotNull(message = "Il valore inserito non puo' essere nullo!")
        Double insertedValue,

        @NotNull(message = "Il valore di riferimento non puo' essere nullo!")
        Double referenceValue,

        @NotNull(message = "L'unità di misura non puo' essere nulla!")
        String unit,

        String pathologicalCondition

) {
}
