package org.example.restfullapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime appointmentDate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}