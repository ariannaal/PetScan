package com.example.PetScan.controllers;

import com.example.PetScan.entities.Symptom;
import com.example.PetScan.payloads.SymptomsDTO;
import com.example.PetScan.services.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/symptoms")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    @GetMapping("/disease/{diseaseId}")
    public SymptomsDTO  getSymptomsByDiseaseId(@PathVariable UUID diseaseId) {

        List<Symptom> symptoms = symptomService.getSymptomsByDiseaseId(diseaseId);
        SymptomsDTO result = null;

        if(!symptoms.isEmpty())
            result =    new SymptomsDTO(symptoms.get(0).getId(), List.of(symptoms.get(0).getSymptomDescription().trim().split(",")));

        return result;
    }
}
