package com.example.PetScan.repositories;

import com.example.PetScan.entities.ValuesName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ValuesNameRepository extends JpaRepository<ValuesName, UUID> {

    Optional<ValuesName> findByNameOfValue(String nameOfValue);

    Optional<ValuesName> findById(UUID id);

}
