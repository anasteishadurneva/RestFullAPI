package org.example.restfullapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PatientNotFoundException.class)
    public String handlePatientNotFound(PatientNotFoundException ex) {
        log.error("Пациент не найден");
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PatientAddException.class)
    public String handlePatientAdd(PatientAddException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AppointmentNotFoundException.class)
    public String handleAppointmentNotFound(AppointmentNotFoundException ex) {
        log.error("Запись на прием не найдена: {}");
        return ex.getMessage();
    }
}