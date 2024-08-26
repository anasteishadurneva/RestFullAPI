package org.example.restfullapi.exception;

public class PatientUpdateException extends RuntimeException {
    public PatientUpdateException(String message) {
        super(message);
    }

    public PatientUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
