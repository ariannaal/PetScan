package com.example.PetScan.entities;

import com.example.PetScan.enums.AbnormalValueLevel;
import jakarta.persistence.*;

import java.util.List;
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

    @ManyToMany
    @JoinTable(name = "disease_test_results", joinColumns = @JoinColumn(name = "disease_test_id"), inverseJoinColumns = @JoinColumn(name = "result_id"))
    private List<Result> results;

    @OneToOne
    private ValuesName abnormalValueName;

    private int threshold;

    @Enumerated(EnumType.STRING)
    private AbnormalValueLevel abnormalValueLevel;

    public DiseaseTest() {
    }

    public DiseaseTest(Disease disease, List<Result> results, ValuesName abnormalValueName, int threshold, AbnormalValueLevel abnormalValueLevel) {
        this.disease = disease;
        this.results = results;
        this.abnormalValueName = abnormalValueName;
        this.threshold = threshold;
        this.abnormalValueLevel = abnormalValueLevel;
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

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public ValuesName getAbnormalValueName() {
        return abnormalValueName;
    }

    public void setAbnormalValueName(ValuesName abnormalValueName) {
        this.abnormalValueName = abnormalValueName;
    }

    public AbnormalValueLevel getAbnormalValueLevel() {
        return abnormalValueLevel;
    }

    public void setAbnormalValueLevel(AbnormalValueLevel abnormalValueLevel) {
        this.abnormalValueLevel = abnormalValueLevel;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
