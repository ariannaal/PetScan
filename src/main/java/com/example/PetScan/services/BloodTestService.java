package com.example.PetScan.services;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.NewBloodTestDTO;
import com.example.PetScan.payloads.NewPetDTO;
import com.example.PetScan.repositories.BloodTestRepository;
import com.example.PetScan.repositories.OwnerRepository;
import com.example.PetScan.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodTestService {

    @Autowired
    private BloodTestRepository bloodTestRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    public BloodTest saveBloodTest(NewBloodTestDTO body) {
        Owner owner = ownerRepository.findById(body.ownerId())
                .orElseThrow(() -> new NotFoundEx("Proprietario non trovato per ID: " + body.ownerId()));

        Pet pet = petRepository.findById(body.petId())
                .orElseThrow(() -> new NotFoundEx("Animale non trovato per ID: " + body.petId()));

        BloodTest newBloodTest = new BloodTest(body.testNumber(), pet, owner, body.dateOfTest(), body.petType());

        BloodTest savedBloodTest = bloodTestRepository.save(newBloodTest);
        System.out.println("L'esame numero " + savedBloodTest.getTestNumber() + " del " + savedBloodTest.getPet().getPetType() + " " + savedBloodTest.getPet().getName() + " è stato salvato con successo!");
        return savedBloodTest;
    }

    public List<BloodTest> findAll() {
        return bloodTestRepository.findAll();
    }

    public Optional<Owner> findByPet(Pet pet){
        return bloodTestRepository.findByPet(pet);
    }

}
