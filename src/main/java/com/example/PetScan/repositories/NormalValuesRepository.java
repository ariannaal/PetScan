package com.example.PetScan.repositories;

import com.example.PetScan.entities.NormalValues;
import com.example.PetScan.entities.ValuesName;
import com.example.PetScan.enums.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NormalValuesRepository extends JpaRepository<NormalValues, UUID> {

    NormalValues findByValuesNameAndPetType(ValuesName valuesName, PetType petType);

    NormalValues findByValuesName(ValuesName valuesName);

    @Query("SELECT n FROM NormalValues n WHERE n.valuesName.id = :id")
    List<NormalValues> findByValuesValuesNameId(@Param("id") UUID id);

    Optional<NormalValues> findById(UUID id);

    List<NormalValues> findByValuesNameIdAndPetType(UUID valuesNameId, PetType petType);


    List<NormalValues> findByPetType(PetType petType);
}
