package com.example.PetScan.repositories;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {

    List<Result> findByBloodTest(BloodTest bloodTest);

}
