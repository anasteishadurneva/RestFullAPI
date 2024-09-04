package org.example.restfullapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restfullapi.config.Patient;
import org.example.restfullapi.config.PatientMapper;
import org.example.restfullapi.dto.PatientDTO;
import org.example.restfullapi.exception.PatientAddException;
import org.example.restfullapi.exception.PatientNotFoundException;
import org.example.restfullapi.kafka.OutboxMessage;
import org.example.restfullapi.kafka.OutboxRepository;
import org.example.restfullapi.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final OutboxRepository outboxRepository;

    public Page<PatientDTO> list(int page, int size) {
        log.info("Получение списка пациентов для страницы {} с размером {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(pageable).map(patientMapper::toDTO);
    }

    public PatientDTO getById(Long patientId)  {
        log.info("Поиск пациента с идентификатором: {}", patientId);
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Пациент не найден"));
        return patientMapper.toDTO(patient);
    }

    @Transactional
    public PatientDTO add(PatientDTO patientDTO) {
        log.info("Добавление нового пациента: {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);

        if (patientRepository.existsById(patient.getId())) {
            throw new PatientAddException("Такой пациент уже существует");
        }
        Patient savedPatient = patientRepository.save(patient);

        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setMessage("Пациент добавлен: " + savedPatient.getId());
        outboxMessage.setSent(false);
        outboxRepository.save(outboxMessage);

        return patientMapper.toDTO(savedPatient);
    }

    public PatientDTO delete(Long patientId) {
        log.info("Удаление пациента с идентификатором: {}", patientId);
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Пациент не найден"));
        patientRepository.delete(patient);
        return patientMapper.toDTO(patient);
    }

    public PatientDTO updateByParts(Patient patient) {
        log.info("Обновление пациента по частям с идентификатором: {}", patient.getId());

        Patient existingPatient = patientRepository.findById(patient.getId())
                .orElseThrow(() -> new PatientNotFoundException("Пациент не найден"));

        if (patient.getName() != null) existingPatient.setName(patient.getName());
        if (patient.getDob() != null) existingPatient.setDob(patient.getDob());
        if (patient.getGender() != null) existingPatient.setGender(patient.getGender());
        if (patient.getAddress() != null) existingPatient.setAddress(patient.getAddress());
        if (patient.getNumber() != null) existingPatient.setNumber(patient.getNumber());
        if (patient.getEmail() != null) existingPatient.setEmail(patient.getEmail());

        Patient updatedPatient = patientRepository.save(existingPatient);
        return patientMapper.toDTO(updatedPatient);
    }


    public PatientDTO update(Patient patient) {
        log.info("Обновление пациента с идентификатором: {}", patient.getId());

        Patient existingPatient = patientRepository.findById(patient.getId())
                .orElseThrow(() -> new PatientNotFoundException("Пациент не найден"));
        Patient updatedPatient = patientRepository.save(existingPatient);
        return patientMapper.toDTO(updatedPatient);
    }
}