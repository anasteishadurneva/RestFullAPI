package org.example.restfullapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.restfullapi.config.Patient;
import org.example.restfullapi.dto.PatientDTO;
import org.example.restfullapi.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

//принимает запросы и вызывает PatientService

@RestController
@RequiredArgsConstructor
public class PatientController implements PatientControllerInt {

    private final PatientService patientService;

    @Override
    public Page<PatientDTO> list(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        return patientService.list(page, size);
    }

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