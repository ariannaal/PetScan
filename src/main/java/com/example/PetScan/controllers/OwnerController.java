package com.example.PetScan.controllers;

import com.example.PetScan.common.TokenManager;
import com.example.PetScan.entities.Owner;
import com.example.PetScan.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        this.ownerService.findByIdAndDelete(id);
    }

    @GetMapping("/me")
    public Owner findMyAccount() {
        UUID ownerId = TokenManager.GetId(SecurityContextHolder.getContext());
        return this.ownerService.findById(ownerId);
    }

    @PutMapping("/me")
    public Owner updateOwner(@RequestBody Owner updatedOwner, @RequestHeader("Authorization") String token) {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        return ownerService.updateOwner(updatedOwner, securityContext);
    }

}
