package com.example.PetScan.repositories;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.Result;
import com.example.PetScan.enums.PetType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {

    List<Result> findByBloodTest(BloodTest bloodTest);

    @Query("SELECT r FROM Result r WHERE r.bloodTest.id = :bloodTestId")
    List<Result> findByBloodTestId(@Param("bloodTestId") UUID bloodTestId);



}
