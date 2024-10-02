package com.example.PetScan.controllers;

import com.example.PetScan.entities.Result;
import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.payloads.NewResultsDTO;
import com.example.PetScan.services.BloodTestAnalyzer;
import com.example.PetScan.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private BloodTestAnalyzer bloodTestAnalyzer;

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
    public List<String> analyze(@RequestBody @Validated NewResultsDTO newResultsDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestEx(validation.getAllErrors().toString());
        }
        return bloodTestAnalyzer.analyzeBloodTest(newResultsDTO);
    }


}
