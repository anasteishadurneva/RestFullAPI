package org.example.restfullapi.exception;

public class PatientAddException extends RuntimeException {
    public PatientAddException(String message) {
        super(message);
    }

    public PatientAddException(String message, Throwable cause) {
        super(message, cause);
    }
}
