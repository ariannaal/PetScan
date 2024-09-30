package com.example.PetScan.services;

import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.NewPetDTO;
import com.example.PetScan.repositories.OwnerRepository;
import com.example.PetScan.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public Pet savePet(NewPetDTO body) {

        Owner owner = ownerRepository.findById(body.ownerId())
                .orElseThrow(() -> new NotFoundEx("Proprietario non trovato per ID: " + body.ownerId()));

        Pet newPet = new Pet(
                body.petType(),
                body.name(),
                body.breed(),
                body.gender(),
                body.age(),
                body.dateOfBirth(),
                owner
        );

        Pet savedPet = petRepository.save(newPet);

        System.out.println("L'animale " + savedPet.getName() + " e' stato salvato con successo!");

        return savedPet;
    }


}
