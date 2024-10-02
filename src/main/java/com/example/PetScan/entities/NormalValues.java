package com.example.PetScan.entities;

import com.example.PetScan.enums.PetType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "normal_values")
public class NormalValues {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "values_name_id")
    private ValuesName valuesName;

    private Double  minValue;
    private Double  maxValue;
    private String unit;
    @Enumerated(EnumType.STRING)
    private PetType petType;
    

    public NormalValues() {
    }

    public NormalValues(ValuesName valuesName, Double  minValue, Double  maxValue, String unit, Result result) {
        this.valuesName = valuesName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
    }

    public UUID getId() {
        return id;
    }

    public ValuesName getValuesName() {
        return valuesName;
    }

    public void setValuesName(ValuesName valuesName) {
        this.valuesName = valuesName;
    }

    public Double  getMinValue() {
        return minValue;
    }

    public void setMinValue(Double  minValue) {
        this.minValue = minValue;
    }

    public Double  getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double  maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
