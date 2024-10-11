package com.example.PetScan.payloads;

import java.util.List;
import java.util.UUID;

public record SymptomsDTO(
        UUID diseaseId,
        List<String> symptomDescription
) {
}
