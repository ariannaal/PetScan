package com.example.PetScan.repositories;

import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {

    Optional<Pet> findById(UUID id);

    List<Pet> findByOwnerId(UUID ownerId);

}
