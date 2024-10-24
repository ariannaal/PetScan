package com.example.PetScan.controllers;

import com.example.PetScan.entities.*;
import com.example.PetScan.enums.AbnormalValueLevel;
import com.example.PetScan.enums.PetType;
import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.*;
import com.example.PetScan.repositories.DiseaseTestRepository;
import com.example.PetScan.repositories.NormalValuesRepository;
import com.example.PetScan.repositories.SymptomRepository;
import com.example.PetScan.services.BloodTestAnalyzer;
import com.example.PetScan.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
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

    @Autowired
    private SymptomRepository symptomRepository;

    // POST http://localhost:3001/results
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> saveResults(@RequestBody @Validated NewResultsDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestEx(validation.getAllErrors().toString());
        }
        resultService.saveResult(body);

        return Collections.singletonMap("message", "Risultato salvato con successo");
    }

    @GetMapping("/{bloodTestId}/symptoms")
    public SymptomsDTO getSymptomsByBloodTestId(@PathVariable UUID bloodTestId) {
        System.out.println("Richiesta GET per bloodTestId: " + bloodTestId);

        BloodTest bloodTest = bloodTestAnalyzer.getResultsByBloodTestId(bloodTestId);
        if (bloodTest == null) {
            System.out.println("Nessun BloodTest trovato con ID: " + bloodTestId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun BloodTest trovato con ID: " + bloodTestId);
        }

        for (Result result : bloodTest.getResults()) {
            SymptomsDTO resultSymptoms = fetchSymptomsForResult(result);
            if (resultSymptoms != null) {
                return resultSymptoms;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun sintomo trovato per BloodTest ID: " + bloodTestId);
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
    public CompleteBloodTestDTO getResultsByBloodTestId(@PathVariable UUID bloodTestId) {
        System.out.println("Richiesta GET per bloodTestId: " + bloodTestId);

        BloodTest bloodTest = bloodTestAnalyzer.getResultsByBloodTestId(bloodTestId);

        if (bloodTest == null) {
            System.out.println("Nessun BloodTest trovato con ID: " + bloodTestId);
            throw new NotFoundEx("Nessun BloodTest trovato con ID: " + bloodTestId);
        }

        Pet pet = bloodTest.getPet();
        Owner owner = bloodTest.getOwner();

        if (bloodTest.getResults() == null || bloodTest.getResults().isEmpty()) {
            System.out.println("Nessun risultato trovato per il BloodTest: " + bloodTestId);
        }

        // mappo i risultati in ResultValueDTO
        List<ResultValueDTO> resultValues = bloodTest.getResults().stream()
                .map(result -> {
                    List<DiseaseTest> diseaseTests = findDiseaseTestsForResult(result);
                    List<PathologicalConditionDTO> pathologicalConditions = new ArrayList<>();
                    Double referenceValue = result.getValue();
                    List<String> unit = null;

                    AbnormalValueLevel abnormalLevel = null; // inizializzo abnormal level come null

                    for (DiseaseTest diseaseTest : diseaseTests) {
                        if (referenceValue == null) {
                            referenceValue = diseaseTest.getThreshold() != -1 ? (double) diseaseTest.getThreshold() : null;
                        }

                        // Controllo che il valore non sia null prima di eseguire operazioni
                        if (result.getValue() != -1) {
                            if (referenceValue != null) {
                                if (result.getValue() > referenceValue) {
                                    abnormalLevel = AbnormalValueLevel.HIGH;
                                } else if (result.getValue() < referenceValue) {
                                    abnormalLevel = AbnormalValueLevel.LOW;
                                }
                            }
                        }

                        pathologicalConditions.add(new PathologicalConditionDTO(diseaseTest.getDisease().getId(), diseaseTest.getDisease().getDiseaseName(), diseaseTest.getDisease().getDescription()));
                    }

                    String parameterName = result.getValuesName() != null ? result.getValuesName().getNameOfValue() : "N/A";

                    if (result.getValuesName() != null) {
                        UUID valuesNameId = result.getValuesName().getId();

                        List<String> units = (List<String>) normalValuesRepository.findUnitsByValuesNameIdAndPetType(valuesNameId, bloodTest.getPetType());
                        unit = Collections.singletonList(units.isEmpty() ? "N/A" : units.get(0));

                        List<NormalValues> normalValuesList = normalValuesRepository.findByValuesNameIdAndPetType(valuesNameId, bloodTest.getPetType());
                        NormalValues normalValues = (normalValuesList.isEmpty()) ? null : normalValuesList.get(0);

                        Double minValue = (normalValues != null) ? normalValues.getMinValue() : null;
                        Double maxValue = (normalValues != null) ? normalValues.getMaxValue() : null;

                        double minValueSafe = (minValue != null) ? minValue : 0.0;
                        double maxValueSafe = (maxValue != null) ? maxValue : 0.0;

                        String abnormalLevelString = abnormalLevel != null ? abnormalLevel.name() : null;

                        // se non ci sono patologie associate imposto a null
                        String pathologicalConditionString = pathologicalConditions.isEmpty() ? null :
                                String.join(", ", pathologicalConditions.stream().map(PathologicalConditionDTO::name).collect(Collectors.toList())) +
                                        (abnormalLevelString != null ? " (" + abnormalLevelString + ")" : "");

                        SymptomsDTO symptoms = fetchSymptomsForResult(result);

                        return new ResultValueDTO(
                                result.getValue(),
                                minValueSafe,
                                maxValueSafe,
                                result.getValuesName().getId(),
                                parameterName,
                                unit,
                                pathologicalConditions,
                                abnormalLevel,
                                symptoms
                        );
                    }

                    return null; // o un oggetto di default, se necessario
                })
                .filter(Objects::nonNull) // rimuovi eventuali risultati null
                .collect(Collectors.toList());

        // restituisce CompleteBloodTestDTO
        return new CompleteBloodTestDTO(
                bloodTest.getId(),
                bloodTest.getDateOfTest(),
                bloodTest.getTestNumber(),
                bloodTest.getPetType(),
                pet.getName(),
                pet.getBreed(),
                pet.getGender(),
                pet.getAge(),
                owner.getName(),
                owner.getSurname(),
                owner.getDateOfBirth(),
                owner.getEmail(),
                resultValues
        );
    }


    private String getUnitFromNormalValues(UUID valuesNameId) {
        NormalValues normalValue = normalValuesRepository.findByValuesNameId(valuesNameId);
        return normalValue != null ? normalValue.getUnit() : "N/A";
    }

    private SymptomsDTO fetchSymptomsForResult(Result result) {

        SymptomsDTO symptomsDTO = null;

        List<DiseaseTest> diseaseTests = findDiseaseTestsForResult(result);

        for (DiseaseTest diseaseTest : diseaseTests) {
            UUID diseaseId = diseaseTest.getDisease().getId();
            List<Symptom> symptoms = symptomRepository.findByDiseaseId(diseaseId);


            if (!symptoms.isEmpty()) {

                List<String> symtomsDescription = List.of(symptoms.get(0).getSymptomDescription().trim().split(","));

                symptomsDTO = new SymptomsDTO(symptoms.get(0).getId(), symtomsDescription);
                break; // dopo aver trovato il primo sintomo esce dal ciclo
            }
        }

        return symptomsDTO;
    }




    public List<DiseaseTest> findDiseaseTestsForResult(Result result) {
        ValuesName abnormalValueName = result.getValuesName();
        BloodTest bloodTest = result.getBloodTest();
        PetType petType = bloodTest.getPetType();

        List<DiseaseTest> diseaseTests = diseaseTestRepository.findByAbnormalValueNameAndPetType(abnormalValueName, petType);

        return diseaseTests;
    }



    private List<DiseaseTest> getAllDiseaseTests() {
        return diseaseTestRepository.findAll();
    }


}
