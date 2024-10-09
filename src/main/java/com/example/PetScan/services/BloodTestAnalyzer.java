package com.example.PetScan.services;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.DiseaseTest;
import com.example.PetScan.entities.NormalValues;
import com.example.PetScan.entities.Result;
import com.example.PetScan.enums.AbnormalValueLevel;
import com.example.PetScan.enums.PetType;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.BloodTestAnalysisDTO;
import com.example.PetScan.payloads.NewResultsDTO;
import com.example.PetScan.payloads.ResultValueDTO;
import com.example.PetScan.repositories.BloodTestRepository;
import com.example.PetScan.repositories.DiseaseTestRepository;
import com.example.PetScan.repositories.NormalValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BloodTestAnalyzer {

    @Autowired
    private NormalValuesRepository normalValuesRepository;

    @Autowired
    private DiseaseTestRepository diseaseTestRepository;

    @Autowired
    private BloodTestRepository bloodTestRepository;

    public BloodTest getResultsByBloodTestId(UUID bloodTestId) {
        return bloodTestRepository.findById(bloodTestId)
                .orElseThrow(() -> new NotFoundEx("Esame del sangue non trovato con id: " + bloodTestId));
    }

    // confronto tra valori normali e valori patologici dell'input di valori ricevuti dall'esame del sangue
    public List<BloodTestAnalysisDTO> analyzeBloodTest(NewResultsDTO newResultsDTO) {
        List<BloodTestAnalysisDTO> analysisResults = new ArrayList<>();

        List<ResultValueDTO> results = newResultsDTO.results();

        PetType petType = bloodTestRepository.findPetTypeByBloodTestId(newResultsDTO.bloodTestId());

        for (ResultValueDTO result : results) {
            UUID id = result.valuesNameId();
            Double value = Double.valueOf(result.value());


            List<NormalValues> normalValueList = normalValuesRepository.findByValuesNameIdAndPetType(id, petType);

            for (NormalValues normalValue : normalValueList) {
                String valueName = normalValue.getValuesName().getNameOfValue();
                Double minValue = normalValue.getMinValue();
                Double maxValue = normalValue.getMaxValue();
                String unit = normalValue.getUnit();
                String pathologicalCondition = null;

                //determino il valore di riferimento
                Double referenceValue;
                if (value < minValue) {
                    referenceValue = minValue;  // valore minimo in caso di anomalia
                } else {
                    referenceValue = maxValue;   // valore massimo in caso di anomalia
                }

                // confronto il valore del parametro con i range minimo e massimi entro cui considerato normale
                if (value < minValue || value > maxValue) {
                    // trova se ci sono le patologie associate al valore anomalo
                    List<DiseaseTest> diseaseTests = diseaseTestRepository.findByAbnormalValueNameAndPetType(normalValue.getValuesName(), petType);
                    for (DiseaseTest diseaseTest : diseaseTests) {
                        if (value < minValue && diseaseTest.getAbnormalValueLevel() == AbnormalValueLevel.LOW) {
                            pathologicalCondition = diseaseTest.getDisease().getDiseaseName();
                        } else if (value > maxValue && diseaseTest.getAbnormalValueLevel() == AbnormalValueLevel.HIGH) {
                            pathologicalCondition = diseaseTest.getDisease().getDiseaseName();
                        }
                    }

                    analysisResults.add(new BloodTestAnalysisDTO(
                            valueName,
                            value,
                            referenceValue,
                            unit,
                            pathologicalCondition // possibile patologia associata
                    ));
                } else {
                    analysisResults.add(new BloodTestAnalysisDTO(
                            valueName,
                            value,
                            referenceValue,
                            unit,
                            null  // nessun quadro patologico in caso di valore normale
                    ));
                }
            }
        }
        return analysisResults;
    }
}