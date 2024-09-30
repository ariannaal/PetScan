package com.example.PetScan.services;

import com.example.PetScan.entities.Owner;
import com.example.PetScan.exceptions.UnauthorizedEx;
import com.example.PetScan.payloads.OwnerLoginDTO;
import com.example.PetScan.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(OwnerLoginDTO body) {

        Owner found = this.ownerService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {

            return jwtTools.createToken(found);
        } else {

            throw new UnauthorizedEx("Credenziali errate!");
        }

    }
}
