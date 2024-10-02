package org.example.restfullapi.controller.impl;

import lombok.RequiredArgsConstructor;
import org.example.restfullapi.controller.PatientController;
import org.example.restfullapi.entity.Patient;
import org.example.restfullapi.dto.PatientDTO;
import org.example.restfullapi.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

//принимает запросы и вызывает PatientService

@RestController
@RequiredArgsConstructor
public class PatientControllerImpl implements PatientController {

    private final PatientService patientService;

    @Override
    public Page<PatientDTO> list(int page,int size) {return patientService.list(page, size);}

    @Override
    public PatientDTO getById(Long id) {return patientService.getById(id);}

    @PostMapping(path = "item")
    @Override
    public PatientDTO add(PatientDTO patient) {return patientService.add(patient);}

    @Override
    public PatientDTO delete(Long id) {return patientService.delete(id);}

    @Override
    public PatientDTO updateByParts(Patient patient) {return patientService.updateByParts(patient);}

    @Override
    public PatientDTO update(Patient patient) {return patientService.update(patient);}

}