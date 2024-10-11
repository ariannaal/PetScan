package com.example.PetScan.controllers;

import com.example.PetScan.common.TokenManager;
import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.payloads.NewBloodTestDTO;
import com.example.PetScan.services.BloodTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/bloodTests")
public class BloodTestController {

    @Autowired
    private BloodTestService bloodTestService;


    // POST http://localhost:3001/bloodTests
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodTest saveBloodTest(@RequestBody @Validated NewBloodTestDTO body, BindingResult validation) {

        BloodTest bloodTest = new BloodTest();

        try{
            UUID ownerId = TokenManager.GetId(SecurityContextHolder.getContext());

            NewBloodTestDTO newBloodTestDTO = new NewBloodTestDTO(
                    body.dateOfTest(),
                    body.petType(),
                    body.testNumber(),
                    ownerId,
                    body.petId()
            );

            bloodTest = bloodTestService.saveBloodTest(newBloodTestDTO);


        }catch (Exception e){
            System.out.println("Errore durante il salvataggio del test del sangue: " + e.getMessage());
        }

        return bloodTest;
    }





}
