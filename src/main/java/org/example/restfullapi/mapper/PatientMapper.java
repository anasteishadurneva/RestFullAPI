package org.example.restfullapi.mapper;

import org.example.restfullapi.dto.PatientDTO;
import org.example.restfullapi.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDTO(Patient patient);
    Patient toEntity(PatientDTO patientDTO);
}