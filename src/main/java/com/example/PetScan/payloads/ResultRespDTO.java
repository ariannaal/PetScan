package com.example.PetScan.payloads;

public record ResultRespDTO(
        String name,
        String value,
        Double referenceValue,
        String pathology
) {}