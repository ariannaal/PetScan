package com.example.PetScan.services;

import com.example.PetScan.entities.Symptom;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.repositories.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    public List<Symptom> getSymptomsByDiseaseId(UUID diseaseId) {
        List<Symptom>  symptom = symptomRepository.findByDiseaseId(diseaseId);
        if (symptom.isEmpty()) {
            throw new NotFoundEx("Nessun sintomo trovato per la patologia con id: " + diseaseId);
        }
        return symptom;
    }

}
