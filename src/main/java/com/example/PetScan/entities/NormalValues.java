package com.example.PetScan.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "normal_values")
public class NormalValues {

    @Id
    @GeneratedValue
    private UUID id;

    private String testName;
    private String minValue;
    private String maxValue;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "blood_test_id")
    private BloodTest bloodTest;

    @ManyToOne
    @JoinColumn(name = "results_id")
    private Result result;

    public NormalValues() {
    }

    public NormalValues(String testName, String minValue, String maxValue, String unit, BloodTest bloodTest, Result result) {
        this.testName = testName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.bloodTest = bloodTest;
        this.result = result;
    }

    public UUID getId() {
        return id;
    }


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BloodTest getBloodTest() {
        return bloodTest;
    }

    public void setBloodTest(BloodTest bloodTest) {
        this.bloodTest = bloodTest;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
