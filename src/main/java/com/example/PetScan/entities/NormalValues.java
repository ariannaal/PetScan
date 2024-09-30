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

    private String minValue;
    private String maxValue;
    private String unit;
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "results_id")
    private Result result;

    public NormalValues() {
    }

    public NormalValues(ValuesName valuesName, String minValue, String maxValue, String unit, Result result) {
        this.valuesName = valuesName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.result = result;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
