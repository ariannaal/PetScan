package com.example.PetScan.services;

import com.example.PetScan.entities.Owner;
import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.NewOwnerDTO;
import com.example.PetScan.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Owner save(NewOwnerDTO body) {

        this.ownerRepository.findByEmail(body.email()).ifPresent(

                user -> {
                    throw new BadRequestEx("La mail " + body.email() + " e' gia' in uso!");
                }
        );

        Owner newOwner = new Owner(body.name(), body.surname(), body.gender(), body.dateOfBirth(), body.email(), bcrypt.encode(body.password()));

        Owner savedOwner = this.ownerRepository.save(newOwner);

        System.out.println("Il proprietario " + savedOwner + " e' stato salvato con successo!");

        return savedOwner;

    }

    public Page<Owner> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ownerRepository.findAll(pageable);
    }

    public Owner findById(UUID id) {
        return this.ownerRepository.findById(id).orElseThrow(() -> new NotFoundEx(String.valueOf(id)));
    }

    public Owner findByEmail(String email) {
        return ownerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEx("Utente non trovato con email: " + email));
    }

    public void findByIdAndDelete(UUID id) {
        Owner found = this.findById(id);
        this.ownerRepository.delete(found);
    }

    public Owner findOwnerById(UUID ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner con id " + ownerId + " non trovato"));
    }
}
