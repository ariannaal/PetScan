package com.example.PetScan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "values_names")
public class ValuesName {

    @Id
    @GeneratedValue
    private UUID id;

    private String nameOfValue;

    public ValuesName() {
    }

    public ValuesName(String nameOfValue) {
        this.nameOfValue = nameOfValue;
    }

    public UUID getId() {
        return id;
    }


    public String getNameOfValue() {
        return nameOfValue;
    }

    public void setNameOfValue(String nameOfValue) {
        this.nameOfValue = nameOfValue;
    }
}
