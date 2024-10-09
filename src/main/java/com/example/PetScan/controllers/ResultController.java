package com.example.PetScan.controllers;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.DiseaseTest;
import com.example.PetScan.entities.NormalValues;
import com.example.PetScan.entities.Result;
import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.payloads.BloodTestAnalysisDTO;
import com.example.PetScan.payloads.NewResultsDTO;
import com.example.PetScan.payloads.ResultValueDTO;
import com.example.PetScan.repositories.DiseaseTestRepository;
import com.example.PetScan.repositories.NormalValuesRepository;
import com.example.PetScan.services.BloodTestAnalyzer;
import com.example.PetScan.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private BloodTestAnalyzer bloodTestAnalyzer;

    @Autowired
    private DiseaseTestRepository diseaseTestRepository;

    @Autowired
    private NormalValuesRepository normalValuesRepository;

    // POST http://localhost:3001/results
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveResults(@RequestBody @Validated NewResultsDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestEx(validation.getAllErrors().toString());
        }
        resultService.saveResult(body);
    }

    // GET http://localhost:3001/results/bloodTest/{bloodTestId}
    @GetMapping("/bloodTest/{bloodTestId}")
    public List<Result> getResultsByBloodTest(@PathVariable UUID bloodTestId) {
        return resultService.findByBloodTest(bloodTestId);
    }

    @PostMapping("/analyze")
    public List<BloodTestAnalysisDTO> analyze(@RequestBody @Validated NewResultsDTO newResultsDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestEx(validation.getAllErrors().toString());
        }

        List<BloodTestAnalysisDTO> result = bloodTestAnalyzer.analyzeBloodTest(newResultsDTO);
        return result;
    }

    @GetMapping("/{bloodTestId}/values")
    public List<ResultValueDTO> getResultsByBloodTestId(@PathVariable UUID bloodTestId) {
        BloodTest bloodTest = bloodTestAnalyzer.getResultsByBloodTestId(bloodTestId);

        return bloodTest.getResults().stream()
                .map(result -> {
                    List<DiseaseTest> diseaseTests = findDiseaseTestsForResult(result);

                    List<String> pathologicalConditions = new ArrayList<>();
                    Double referenceValue = result.getValue();
                    List<String> unit = null;

                    for (DiseaseTest diseaseTest : diseaseTests) {

                        if (referenceValue == null) {
                            // -1 non puo essere valido come valore di riferimento e non puo essere null
                            referenceValue = diseaseTest.getThreshold() != -1 ? (double) diseaseTest.getThreshold() : null;

                        }

                        pathologicalConditions.add(diseaseTest.getDisease().getDiseaseName());
                    }

                    String parameterName = result.getValuesName() != null ? result.getValuesName().getNameOfValue() : "N/A";

                    if (result.getValuesName() != null) {
                        UUID valuesNameId = result.getValuesName().getId();
                        //ricavo l'unita dalla repository
                        unit = normalValuesRepository.findUnitsByValuesNameId(valuesNameId);
                    }

                    return new ResultValueDTO(
                            result.getValue(),
                            result.getValuesName().getId(),
                            parameterName,
                            referenceValue,
                            unit,
                            String.join(", ", pathologicalConditions)
                    );
                })
                .collect(Collectors.toList());
    }


    private String getUnitFromNormalValues(UUID valuesNameId) {
        NormalValues normalValue = normalValuesRepository.findByValuesNameId(valuesNameId);
        return normalValue != null ? normalValue.getUnit() : "N/A";
    }



    private List<DiseaseTest> findDiseaseTestsForResult(Result result) {
        return diseaseTestRepository.findByResultsContains(result);
    }

    private List<DiseaseTest> getAllDiseaseTests() {
        return diseaseTestRepository.findAll();
    }




}
