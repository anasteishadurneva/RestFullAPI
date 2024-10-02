package org.example.restfullapi.mapper;

import org.example.restfullapi.dto.AppointmentDTO;
import org.example.restfullapi.entity.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO toDTO(Appointment appointment);
    Appointment toEntity(AppointmentDTO appointmentDTO);
}
