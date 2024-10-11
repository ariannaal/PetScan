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
    @Column(length = 1500)
    private String description;
    private String treatment;
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @OneToMany(mappedBy = "disease")
    private List<Symptom> symptoms;


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

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }
}
