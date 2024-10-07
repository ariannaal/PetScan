package com.example.PetScan.common;

import com.example.PetScan.entities.Owner;
import org.springframework.security.core.context.SecurityContext;

import java.util.UUID;

// gestisco il token in maniera centralizzata per garantire manutenibilita
public class TokenManager {

    public static UUID GetId(SecurityContext securityContext){
        UUID ownerId = ((Owner) securityContext.getAuthentication().getPrincipal()).getId();
        return ownerId;
    }
}
