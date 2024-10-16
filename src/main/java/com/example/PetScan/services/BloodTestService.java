package com.example.PetScan.services;

import com.example.PetScan.entities.*;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.NewBloodTestDTO;
import com.example.PetScan.payloads.NewPetDTO;
import com.example.PetScan.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BloodTestService {

    @Autowired
    private BloodTestRepository bloodTestRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private NormalValuesRepository normalValuesRepository;

    @Autowired
    private DiseaseTestRepository diseaseTestRepository;

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

    public List<BloodTest> findByPetId(UUID petId) {
        return bloodTestRepository.findByPetId(petId);
    }
    public BloodTest findById(UUID petId) {
        return bloodTestRepository.findById(petId).orElse(null);
    }

    public void deleteBloodTest(UUID bloodTestId) {
        BloodTest bloodTest = bloodTestRepository.findById(bloodTestId)
                .orElseThrow(() -> new IllegalArgumentException("Test del sangue non trovato con ID: " + bloodTestId));

        bloodTestRepository.delete(bloodTest);
    }



}
