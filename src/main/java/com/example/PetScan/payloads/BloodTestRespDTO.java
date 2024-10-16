package com.example.PetScan.payloads;

import com.example.PetScan.enums.PetType;

import java.time.LocalDate;
import java.util.UUID;

public record BloodTestRespDTO(
        UUID id,
        int testNumber,
        LocalDate dateOfTest,
        PetType petType,
        UUID petId
) {
}
