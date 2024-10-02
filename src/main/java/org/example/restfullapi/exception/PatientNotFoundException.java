package org.example.restfullapi.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        System.out.println(message);
    }
}

