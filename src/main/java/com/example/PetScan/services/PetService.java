package com.example.PetScan.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.PetScan.entities.Owner;
import com.example.PetScan.entities.Pet;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.NewPetDTO;
import com.example.PetScan.payloads.UpdatePetDTO;
import com.example.PetScan.repositories.OwnerRepository;
import com.example.PetScan.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Pet savePet(NewPetDTO body, UUID ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundEx("Proprietario non trovato per ID: " + ownerId));

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

    public List<Pet> petsList() {
        return petRepository.findAll();
    }

    public Pet findById(UUID id) {
        return petRepository.findById(id).orElseThrow(() -> new NotFoundEx(String.valueOf(id)));
    }

    public void findByIdAndDelete(UUID id) {
        Pet found = this.findById(id);
        petRepository.delete(found);
    }

    public List<Pet> getPetsByOwnerId(UUID ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }

    public Pet findByIdAndUpdate(UUID id, Pet body) {
        Pet found = this.findById(id);
        found.setAge(body.getAge());
        found.setOwner(body.getOwner());
        found.setPicture(body.getPicture());
        return petRepository.save(found);
    }

    public void uploadPicture(UUID id, MultipartFile image) throws IOException {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundEx("Animale con id " + id + " non trovato."));

        Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String picture = uploadResult.get("url").toString();
        System.out.println("URL: " + picture);

        pet.setPicture(picture);
        petRepository.save(pet);
    }

    public Pet findPetById(UUID petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet con ID " + petId + " non trovato"));
    }

    public Pet updatePet(UUID id, UpdatePetDTO petUpdateDTO, MultipartFile picture) {
        try {
            Pet pet = petRepository.findById(id)
                    .orElseThrow(() -> new NotFoundEx("Animale non trovato per ID: " + id));

            pet.setName(petUpdateDTO.name());

            if (petUpdateDTO.age() == null) {
                throw new IllegalArgumentException("L'età non può essere nulla");
            }
            pet.setAge(petUpdateDTO.age());

            if (picture != null && !picture.isEmpty()) {
                String cloudinaryUrl = uploadPictureToCloudinary(picture);
                pet.setPicture(cloudinaryUrl);
            }

            return petRepository.save(pet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante l'aggiornamento dell'animale: " + e.getMessage());
        }
    }

    public String uploadPictureToCloudinary(MultipartFile picture) {
        if (picture == null || picture.isEmpty()) {
            throw new RuntimeException("Nessun file caricato.");
        }

        try {
            // carico il file su cloudinary
            Map uploadResult = cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to Cloudinary", e);
        }

}
    }



