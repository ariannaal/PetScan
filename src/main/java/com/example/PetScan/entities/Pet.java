package com.example.PetScan.entities;

import com.example.PetScan.enums.Gender;
import com.example.PetScan.enums.PetType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PetType petType;
    private String name;
    private String breed;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;
    private LocalDate dateOfBirth;
    private String picture;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "pet")
    private List<BloodTest> bloodTests;

    public Pet() {
    }

    public Pet(PetType petType, String name, String breed, Gender gender, int age, LocalDate dateOfBirth, String picture, Owner owner) {
        this.petType = petType;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.picture = picture;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public PetType getPetType() {
        return petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
