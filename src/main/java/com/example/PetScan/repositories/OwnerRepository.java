package com.example.PetScan.repositories;

import com.example.PetScan.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    Optional<Owner> findByEmail(String email);

    boolean existsByEmail(String email);

}
