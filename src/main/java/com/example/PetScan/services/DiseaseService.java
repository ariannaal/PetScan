package com.example.PetScan.services;

import com.example.PetScan.entities.Disease;
import com.example.PetScan.entities.Symptom;
import com.example.PetScan.enums.PetType;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.repositories.DiseaseRepository;
import com.example.PetScan.repositories.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    public List<String> getSymptomsByDiseaseId(UUID diseaseId) {
        return diseaseRepository.findById(diseaseId)
                .map(disease -> disease.getSymptoms()
                        .stream()
                        .map(Symptom::getSymptomDescription)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new NotFoundEx("Patologia non trovata"));
    }





}


