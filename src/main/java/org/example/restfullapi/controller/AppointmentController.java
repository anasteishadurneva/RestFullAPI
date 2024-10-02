package org.example.restfullapi.controller;

import org.example.restfullapi.dto.AppointmentDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentController {
    @PostMapping
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    @DeleteMapping("/{id}")
    void cancelAppointment(UUID id);
    @GetMapping("/patient/{patientId}")
    List<AppointmentDTO> getAppointmentsByPatient(Long patientId);
    @GetMapping("/date")
    List<AppointmentDTO> getAppointmentsByDate(LocalDateTime date);
}