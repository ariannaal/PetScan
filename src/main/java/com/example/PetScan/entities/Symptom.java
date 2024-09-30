package com.example.PetScan.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "symptoms")
public class Symptom {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "disease_id")
    private Disease disease;

    private String symptomDescription;

    public Symptom() {
    }

    public Symptom(Disease disease, String symptomDescription) {
        this.disease = disease;
        this.symptomDescription = symptomDescription;
    }

    public UUID getId() {
        return id;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public String getSymptomDescription() {
        return symptomDescription;
    }

    public void setSymptomDescription(String symptomDescription) {
        this.symptomDescription = symptomDescription;
    }
}
