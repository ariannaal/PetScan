package com.example.PetScan.payloads;

import com.example.PetScan.enums.AbnormalValueLevel;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ResultValueDTO(

        double value,
        double minValue,
        double maxValue,

        @NotNull(message = "L'id del valore e' obbligatorio!")
        UUID valuesNameId,

        String valueName,
        List<String> unit,
        List<PathologicalConditionDTO> pathologicalConditions,
        AbnormalValueLevel abnormalLevel,
        SymptomsDTO symptoms

) {
}
