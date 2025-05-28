package org.example.restfullapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PUBLIC)
public class AppointmentStatsDTO {
    @JsonProperty("date")
    LocalDate date;
    @JsonProperty("count_new_appointments")
    long countNewAppointments;
}