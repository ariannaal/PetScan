package com.example.PetScan.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "blood_tests")
public class BloodTest {

    @Id
    @GeneratedValue
    private UUID id;

    private String testName;
    private double minValue;
    private double maxValue;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public BloodTest() {
    }

    public BloodTest(String testName, double minValue, double maxValue, Pet pet) {
        this.testName = testName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.pet = pet;
    }

    public UUID getId() {
        return id;
    }

    public String getTestName() {
        return testName;
    }


    public double getMinValue() {
        return minValue;
    }


    public double getMaxValue() {
        return maxValue;
    }


    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
