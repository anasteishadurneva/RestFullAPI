package org.example.restfullapi.controller.impl;

import lombok.RequiredArgsConstructor;
import org.example.restfullapi.controller.AppointmentController;
import org.example.restfullapi.dto.AppointmentDTO;
import org.example.restfullapi.entity.Patient;
import org.example.restfullapi.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class AppointmentControllerImpl implements AppointmentController {

    private final AppointmentService appointmentService;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {return appointmentService.createAppointment(appointmentDTO);}

    @Override
    public void cancelAppointment(UUID id) {appointmentService.cancelAppointment(id);}

    @Override
    public Page<AppointmentDTO> getAppointmentsByPatient(Patient patientId, int page,int size) {return appointmentService.getAppointmentsByPatient(patientId, page, size); }

    @Override
    public Page<AppointmentDTO> getAppointmentsByDate(LocalDate date, int page,int size) {return appointmentService.getAppointmentsByDate(date, page, size);}

    @Override
    public long getAppointmentsCountByPatientId(Long id){return appointmentService.countAppointmentsForPatient(id);}

    @Override
    public Page<AppointmentDTO> getAppointmentsFromDateToToday(LocalDate startDate, int page,int size){return appointmentService.getAppointmentsFromDateToToday(startDate, page, size);}
}