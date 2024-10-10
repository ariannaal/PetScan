package com.example.PetScan.payloads;

import com.example.PetScan.enums.Gender;
import com.example.PetScan.enums.PetType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CompleteBloodTestDTO(

        //dati sul bloodtest
        UUID bloodTestId,
        LocalDate dateOfTest,
        int testNumber,
        PetType petType,

        //dati sull'animale
        String petName,
        String breed,
        Gender gender,
        int age,

        //dati sull'owner
        String ownerName,
        String surname,
        LocalDate dateOfBirth,
        String email,

        //lista di risultati
        List<ResultValueDTO> results




) {
}
