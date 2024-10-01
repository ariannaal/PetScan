package com.example.PetScan.controllers;

import com.example.PetScan.entities.BloodTest;
import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.payloads.NewBloodTestDTO;
import com.example.PetScan.services.BloodTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bloodTests")
public class BloodTestController {

    @Autowired
    private BloodTestService bloodTestService;

    // POST http://localhost:3001/bloodTest
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodTest saveBloodTest(@RequestBody @Validated NewBloodTestDTO body, BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestEx(validation.getAllErrors().toString());
        }

        return bloodTestService.saveBloodTest(body);
    }

}
