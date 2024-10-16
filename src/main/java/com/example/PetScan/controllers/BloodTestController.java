package com.example.PetScan.controllers;

import com.example.PetScan.common.TokenManager;
import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.Result;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.BloodTestRespDTO;
import com.example.PetScan.payloads.NewBloodTestDTO;
import com.example.PetScan.services.BloodTestService;
import com.example.PetScan.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bloodTests")
public class BloodTestController {

    @Autowired
    private BloodTestService bloodTestService;

    @Autowired
    private ResultService resultService;


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


    @GetMapping("/{petId}")
    public List<BloodTestRespDTO> getBloodTestsByPetId(@PathVariable UUID petId) {
        List<BloodTest> bloodTests = bloodTestService.findByPetId(petId);

        // se non ci sono esami torna un array vuoto
        if (bloodTests == null || bloodTests.isEmpty()) {
            return Collections.emptyList();
        }

        return bloodTests.stream()
                .map(bloodTest -> new BloodTestRespDTO(
                        bloodTest.getId(),
                        bloodTest.getTestNumber(),
                        bloodTest.getDateOfTest(),
                        bloodTest.getPetType(),
                        bloodTest.getPet().getId()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/results/{bloodTestId}")
    public List<Result> getResultsByBloodTestId(@PathVariable UUID bloodTestId) {
        System.out.println("Richiesta GET per bloodTestId: " + bloodTestId);
        List<Result> results = resultService.getResultsByBloodTestId(bloodTestId);
        System.out.println("Risultati ottenuti: " + results);
        return results;
    }

    // DELETE http://localhost:3001/bloodTests/{bloodTestId}
    @DeleteMapping("/{bloodTestId}")
    public void deleteBloodTest(@PathVariable UUID bloodTestId) {
        try {
            bloodTestService.deleteBloodTest(bloodTestId);
        } catch (IllegalArgumentException e) {
            System.out.println("Errore durante l'eliminazione dell'esame del sangue: " + e.getMessage());
        }
    }



}
