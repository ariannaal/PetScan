package com.example.PetScan.payloads;

public record UpdatePetDTO(

        String name,
        Integer  age,
        String  picture
) {
}
