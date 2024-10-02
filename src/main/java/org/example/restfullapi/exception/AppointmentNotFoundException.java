package org.example.restfullapi.exception;


public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message){
        System.out.println(message);
    }
}
