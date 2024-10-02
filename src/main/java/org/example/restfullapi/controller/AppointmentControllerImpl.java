package org.example.restfullapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restfullapi.dto.AppointmentDTO;
import org.example.restfullapi.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentControllerImpl implements AppointmentController {

    private final AppointmentService appointmentService;

    @Override
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.createAppointment(appointmentDTO);
    }

    @Override
    public void cancelAppointment(@PathVariable UUID id) {
        appointmentService.cancelAppointment(id);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDate(@RequestParam LocalDateTime date) {
        return appointmentService.getAppointmentsByDate(date);
    }
}