package com.example.PetScan.entities;

import com.example.PetScan.enums.PetType;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue
    private UUID id;

    private String diseaseName;
    private String description;
    private String treatment;
    @Enumerated(EnumType.STRING)
    private PetType petType;


    public Disease() {
    }

    public Disease(String diseaseName, String description, String treatment, PetType petType) {
        this.diseaseName = diseaseName;
        this.description = description;
        this.treatment = treatment;
        this.petType = petType;
    }

    public UUID getId() {
        return id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }
}
