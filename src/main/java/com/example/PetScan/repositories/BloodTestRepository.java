package com.example.PetScan.repositories;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BloodTestRepository extends JpaRepository<BloodTest, UUID> {

    Optional<Owner> findByPet(Pet pet);


}
