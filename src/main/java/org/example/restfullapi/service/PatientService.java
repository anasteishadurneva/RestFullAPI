package org.example.restfullapi.service;

import lombok.extern.slf4j.Slf4j;
import org.example.restfullapi.config.Patient;
import org.example.restfullapi.dto.PatientDTO;
import org.example.restfullapi.exception.PatientNotFoundException;
import org.example.restfullapi.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> list() {
        log.info("Listing all patients");
        patientRepository.findAll();
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOs = new ArrayList<>();
        for (Patient patient : patients) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(patient.getId());
            patientDTO.setName(patient.getName());
            patientDTOs.add(patientDTO);
        }
        return patientDTOs;
    }

    public PatientDTO add(PatientDTO patientDTO) {
        Patient patient = new Patient().builder().name(patientDTO.getName()).build();
        log.info("Adding a new patient: {}", patient);
        patientRepository.save(patient);
        return patientDTO;
    }

    public void delete(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        log.info("Deleting patient with ID: {}", patientId);
        patientRepository.deleteById(patientId);
    }

    public PatientDTO update(PatientDTO patientDTO) {
        log.info("Updating patient with ID: {}", patientDTO.getId());
        Patient patient = patientRepository.findById(patientDTO.getId()).get();
        patient.setName(patientDTO.getName());
        patientRepository.save(patient);
        return patientDTO;

    }

    public List<PatientDTO> findById(Long id) {
        log.info("Finding patient with ID: {}", id);
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOs = new ArrayList<>();
        for (Patient patient : patients) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(patient.getId());
        }
        return patientDTOs;
    }
}