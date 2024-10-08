package com.example.PetScan.entities;

import com.example.PetScan.enums.PetType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "blood_tests")
public class BloodTest {

    @Id
    @GeneratedValue
    private UUID id;

    private int testNumber;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private LocalDate dateOfTest;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    @OneToMany(mappedBy = "bloodTest")
    private List<Result> results;

    public BloodTest() {
    }

    public BloodTest(int testNumber, Pet pet, Owner owner, LocalDate dateOfTest, PetType petType) {
        this.testNumber = testNumber;
        this.pet = pet;
        this.owner = owner;
        this.dateOfTest = dateOfTest;
        this.petType = petType;
    }

    public UUID getId() {
        return id;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getDateOfTest() {
        return dateOfTest;
    }

    public void setDateOfTest(LocalDate dateOfTest) {
        this.dateOfTest = dateOfTest;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
