package com.example.PetScan.payloads;

import java.util.List;

public record DiseaseRespDTO(

        String diseaseName,
        String description,
        String treatment,
        List<String> symptoms

) {
}
