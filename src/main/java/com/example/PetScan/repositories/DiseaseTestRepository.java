package com.example.PetScan.repositories;

import com.example.PetScan.entities.DiseaseTest;
import com.example.PetScan.entities.Result;
import com.example.PetScan.entities.ValuesName;
import com.example.PetScan.enums.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiseaseTestRepository extends JpaRepository<DiseaseTest, UUID> {

    Optional<DiseaseTest> findById(UUID id);

    List<DiseaseTest> findByAbnormalValueName(ValuesName abnormalValueName);

    @Query("SELECT dt FROM DiseaseTest dt JOIN dt.disease d WHERE dt.abnormalValueName = :valuesName AND d.petType = :petType")
    List<DiseaseTest> findByAbnormalValueNameAndPetType(@Param("valuesName") ValuesName valuesName, @Param("petType") PetType petType);

    List<DiseaseTest> findByResultsContains(Result result);
}
