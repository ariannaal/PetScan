package com.example.PetScan.repositories;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import com.example.PetScan.enums.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BloodTestRepository extends JpaRepository<BloodTest, UUID> {

    Optional<Owner> findByPet(Pet pet);

    @Query("SELECT bt.petType FROM BloodTest bt WHERE bt.id = :bloodTestId")
    PetType findPetTypeByBloodTestId(@Param("bloodTestId") UUID bloodTestId);

    List<BloodTest> findByOwnerIdAndPetId(UUID ownerId, UUID petId);

    List<BloodTest> findByPetId(UUID petId);



}
