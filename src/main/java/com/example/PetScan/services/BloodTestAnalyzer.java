package com.example.PetScan.services;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.DiseaseTest;
import com.example.PetScan.entities.NormalValues;
import com.example.PetScan.entities.Result;
import com.example.PetScan.enums.PetType;
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

    // confronto tra valori normali e valori patologici dell'input di valori ricevuti dall'esame del sangue
    public List<String> analyzeBloodTest(NewResultsDTO newResultsDTO) {
        List<String> analysisResults = new ArrayList<>();

        List<ResultValueDTO> results = newResultsDTO.results();

        PetType petType = bloodTestRepository.findPetTypeByBloodTestId(newResultsDTO.bloodTestId());

        for (ResultValueDTO result : results) { // per ogni risultato su cui ho itero estraggo l'id e il valore
            UUID id = result.valuesNameId();
            Double value = result.value();

            List<NormalValues> normalValueList = normalValuesRepository.findByValuesNameIdAndPetType(id, petType);

            for (int i = 0; i < normalValueList.size(); i++) {
                NormalValues normalValue = normalValueList.get(i);
                String valueName = normalValue.getValuesName().getNameOfValue();

               // confronto il valore del parametro con i range minimo e massimi entro cui considerato normale
                if (value < normalValue.getMinValue() || value > normalValue.getMaxValue()) {
                    analysisResults.add(valueName + " è anormale: " + value + " " + normalValue.getUnit());

                    // cerco patologie associate al valore anomalo
                    List<DiseaseTest> diseaseTests = diseaseTestRepository.findByAbnormalValueNameAndPetType(normalValue.getValuesName(), petType);
                    for (DiseaseTest diseaseTest : diseaseTests) {
                        if (value > diseaseTest.getThreshold()) {
                            analysisResults.add("Possibile quadro patologico: " + diseaseTest.getDisease().getDiseaseName());
                        }
                    }
                } else {
                    analysisResults.add(value + " è normale: " + value + " " + normalValue.getUnit());
                }
            }
        }

        return analysisResults;
    }
}