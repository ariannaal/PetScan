package com.example.PetScan.controllers;

import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.exceptions.UnauthorizedEx;
import com.example.PetScan.payloads.NewPetDTO;
import com.example.PetScan.repositories.OwnerRepository;
import com.example.PetScan.repositories.PetRepository;
import com.example.PetScan.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    // POST http://localhost:3001/pets
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pet savePet(@RequestBody @Validated NewPetDTO body, BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestEx(validation.getAllErrors().toString());
        }

        return petService.savePet(body);
    }

    // GET http://localhost:3001/pets / {id}
    @GetMapping("/{id}")
    private Pet getSinglePet(@PathVariable UUID id){
        return petService.findById(id);
    }

    // **GET http://localhost:3001/pets
    @GetMapping
    public List<Pet> getAllPets() {
        return petService.petsList();
    }


    // POST http://localhost:3001/pets/{id}/picture
    @PostMapping("/{id}/picture")
    public void uploadPicture(@PathVariable UUID id, @RequestParam("picture") MultipartFile image ) throws IOException {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundEx("Animale non trovato per ID: " + id));

        petService.uploadPicture(id, image);
    }


}
