package org.example.restfullapi.config;

import org.example.restfullapi.dto.AppointmentDTO;
import org.example.restfullapi.entity.Appointment;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO toDTO(Appointment appointment);
    Appointment toEntity(AppointmentDTO appointmentDTO);

    List<AppointmentDTO> toDTOList(List<Appointment> appointments);
}
