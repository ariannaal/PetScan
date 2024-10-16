package com.example.PetScan.controllers;

import com.example.PetScan.common.TokenManager;
import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.exceptions.UnauthorizedEx;
import com.example.PetScan.payloads.NewPetDTO;
import com.example.PetScan.payloads.UpdatePetDTO;
import com.example.PetScan.repositories.OwnerRepository;
import com.example.PetScan.repositories.PetRepository;
import com.example.PetScan.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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

        // ottengo l'id del proprietario dall'access token
        UUID ownerId = TokenManager.GetId(SecurityContextHolder.getContext());

        NewPetDTO updatedBody = new NewPetDTO(
                body.name(),
                body.petType(),
                body.breed(),
                body.gender(),
                body.age(),
                body.dateOfBirth()
        );

        return petService.savePet(updatedBody, ownerId);
    }


    // GET http://localhost:3001/pets/{id}
    @GetMapping("/{id}")
    private Pet getSinglePet(@PathVariable UUID id) {
        return petService.findById(id);
    }

    @GetMapping
    public List<Pet> getPets() {
        List<Pet> pets = new ArrayList<>();

        try {
            UUID ownerId = TokenManager.GetId(SecurityContextHolder.getContext());
            pets = petService.getPetsByOwnerId(ownerId);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return pets;
    }

    // POST http://localhost:3001/pets/{id}/picture
    @PostMapping("/{id}/picture")
    public void uploadPicture(@PathVariable UUID id, @RequestParam("picture") MultipartFile image) throws IOException {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundEx("Animale non trovato per ID: " + id));

        petService.uploadPicture(id, image);
    }

    @PostMapping("/{id}")
    public Pet updatePet(
            @PathVariable UUID id,
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestPart(value = "picture", required = false) MultipartFile picture) {

        UpdatePetDTO petUpdateDTO = new UpdatePetDTO(name, age, picture != null ? picture.getOriginalFilename() : null);
        System.out.println("Received UpdatePetDTO: " + petUpdateDTO);

        try {

            Pet updatedPet = petService.updatePet(id, petUpdateDTO, picture);
            return updatedPet;
        } catch (NotFoundEx e) {

            throw new RuntimeException("Animale non trovato per ID: " + id);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'aggiornamento dell'animale: " + e.getMessage());
        }
    }
}

