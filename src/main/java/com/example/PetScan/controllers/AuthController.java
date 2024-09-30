package com.example.PetScan.controllers;

import com.example.PetScan.exceptions.BadRequestEx;
import com.example.PetScan.payloads.NewOwnerDTO;
import com.example.PetScan.payloads.NewOwnerRespDTO;
import com.example.PetScan.payloads.OwnerLoginDTO;
import com.example.PetScan.payloads.OwnerLoginRespDTO;
import com.example.PetScan.services.AuthService;
import com.example.PetScan.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/login")
    public OwnerLoginRespDTO login(@RequestBody OwnerLoginDTO payload) {
        return new OwnerLoginRespDTO(this.authService.checkCredentialsAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewOwnerRespDTO save(@RequestBody @Validated NewOwnerDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestEx("Ci sono stati errori nel payload. " + messages);
        } else {

            return new NewOwnerRespDTO(this.ownerService.save(body).getId());
        }

    }

}
