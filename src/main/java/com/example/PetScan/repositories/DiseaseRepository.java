package com.example.PetScan.repositories;

import com.example.PetScan.entities.Disease;
import com.example.PetScan.enums.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DiseaseRepository extends JpaRepository<Disease, UUID> {

    Disease findByDiseaseName(String diseaseName);

    @Query("SELECT d FROM Disease d WHERE d.diseaseName = :diseaseName AND d.petType = :petType")
    List<Disease> findByNameAndPetType(@Param("diseaseName") String diseaseName, @Param("petType") PetType petType);
}
