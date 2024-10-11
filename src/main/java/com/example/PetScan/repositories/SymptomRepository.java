package com.example.PetScan.repositories;

import com.example.PetScan.entities.Disease;
import com.example.PetScan.entities.Symptom;
import com.example.PetScan.payloads.SymptomsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SymptomRepository extends JpaRepository<Symptom, UUID> {

    List<Symptom> findByDiseaseId(UUID diseaseId);
}
