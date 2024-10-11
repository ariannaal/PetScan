package com.example.PetScan.controllers;

import com.example.PetScan.entities.Disease;
import com.example.PetScan.enums.PetType;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.DiseaseRespDTO;
import com.example.PetScan.services.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.PetScan.entities.Symptom;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/disease")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @GetMapping("/{diseaseId}")
    public List<String> getSymptoms(@PathVariable("diseaseId") UUID diseaseId) {
        return diseaseService.getSymptomsByDiseaseId(diseaseId);
    }
}
