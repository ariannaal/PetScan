package com.example.PetScan.controllers;

import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.NewPetDTO;
import com.example.PetScan.repositories.OwnerRepository;
import com.example.PetScan.repositories.PetRepository;
import com.example.PetScan.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pet savePet(@RequestBody @Validated NewPetDTO body, BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestEx(validation.getAllErrors().toString());
        }

        return petService.savePet(body);

    }
}
