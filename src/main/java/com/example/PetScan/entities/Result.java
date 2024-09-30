package com.example.PetScan.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "blood_tests_id")
    private BloodTest bloodTest;

    private String value;

    @ManyToOne
    @JoinColumn(name = "values_name_id")
    private ValuesName valuesName;

    public Result() {
    }

    public Result(BloodTest bloodTest, String value, ValuesName valuesName) {
        this.bloodTest = bloodTest;
        this.value = value;
        this.valuesName = valuesName;
    }

    public UUID getId() {
        return id;
    }

    public BloodTest getBloodTest() {
        return bloodTest;
    }

    public void setBloodTest(BloodTest bloodTest) {
        this.bloodTest = bloodTest;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ValuesName getValuesName() {
        return valuesName;
    }

    public void setValuesName(ValuesName valuesName) {
        this.valuesName = valuesName;
    }
}
