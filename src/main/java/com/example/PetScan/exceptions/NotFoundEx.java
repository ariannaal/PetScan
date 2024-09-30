package com.example.PetScan.exceptions;

public class NotFoundEx extends RuntimeException {
    public NotFoundEx(int id) {
        super("La risorsa con id " + id + " non e' stata trovata!");
    }

    public NotFoundEx(String message) {
        super(message);
    }
}
