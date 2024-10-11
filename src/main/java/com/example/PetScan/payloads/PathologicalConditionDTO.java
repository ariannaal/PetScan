package com.example.PetScan.payloads;

import java.util.UUID;

public record PathologicalConditionDTO(
        UUID id,
        String name,
        String description
) {
}
