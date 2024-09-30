package com.example.PetScan.entities;

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


    public Disease() {
    }

    public Disease(String diseaseName, String description, String treatment) {
        this.diseaseName = diseaseName;
        this.description = description;
        this.treatment = treatment;
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
}
