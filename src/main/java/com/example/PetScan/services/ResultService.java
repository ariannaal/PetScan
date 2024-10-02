package com.example.PetScan.services;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.entities.Result;
import com.example.PetScan.entities.ValuesName;
import com.example.PetScan.exceptions.NotFoundEx;
import com.example.PetScan.payloads.NewResultsDTO;
import com.example.PetScan.payloads.ResultValueDTO;
import com.example.PetScan.repositories.BloodTestRepository;
import com.example.PetScan.repositories.ResultRepository;
import com.example.PetScan.repositories.ValuesNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private BloodTestRepository bloodTestRepository;

    @Autowired
    private ValuesNameRepository valuesNameRepository;

    public List<Result> saveResult(NewResultsDTO body) {
        BloodTest bloodTest = bloodTestRepository.findById(body.bloodTestId())
                .orElseThrow(() -> new NotFoundEx("Test del sangue non trovato con ID: " + body.bloodTestId()));

        List<Result> savedResults = new ArrayList<>();

        for (ResultValueDTO resultValueDTO : body.results()) { // itero sulla lista di NewResultDTO che contiene per ciascun parametro il nome e l'id e li recupero

            ValuesName valuesName = valuesNameRepository.findById(resultValueDTO.valuesNameId())
                    .orElseThrow(() -> new NotFoundEx("Nome del valore non trovato con ID: " + resultValueDTO.valuesNameId()));

            Result result = new Result(bloodTest, resultValueDTO.value().toString(), valuesName);

            savedResults.add(resultRepository.save(result));
            System.out.println("I risultati dell'esame nr. " + result.getBloodTest().getTestNumber() + " per l'animale " + result.getBloodTest().getPet().getName() + " sono stati salvati con successo!");
        }

        return savedResults;
    }

    public List<Result> findByBloodTest(UUID bloodTestId) {
        BloodTest bloodTest = bloodTestRepository.findById(bloodTestId)
                .orElseThrow(() -> new NotFoundEx("Esame del sangue non trovato con ID: " + bloodTestId));

        return resultRepository.findByBloodTest(bloodTest);
    }
}
