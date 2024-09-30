package com.example.PetScan.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "disease_tests")
public class DiseaseTest {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @ManyToOne
    @JoinColumn(name = "blood_tests_id")
    private BloodTest bloodTest;

    private String abnormalValue;

    private int threshold;

    public DiseaseTest() {
    }

    public DiseaseTest(Disease disease, BloodTest bloodTest, String abnormalValue, int threshold) {
        this.disease = disease;
        this.bloodTest = bloodTest;
        this.abnormalValue = abnormalValue;
        this.threshold = threshold;
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

    public BloodTest getBloodTest() {
        return bloodTest;
    }

    public void setBloodTest(BloodTest bloodTest) {
        this.bloodTest = bloodTest;
    }

    public String getAbnormalValue() {
        return abnormalValue;
    }

    public void setAbnormalValue(String abnormalValue) {
        this.abnormalValue = abnormalValue;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
