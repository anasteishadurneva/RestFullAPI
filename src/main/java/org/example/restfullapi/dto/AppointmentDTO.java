package org.example.restfullapi.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.restfullapi.entity.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentDTO {
    UUID id;
    LocalDateTime appointmentDate;
    PatientDTO patient;
    AppointmentStatus status;
}