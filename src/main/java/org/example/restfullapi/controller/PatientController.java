package org.example.restfullapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.restfullapi.dto.PatientDTO;
import org.example.restfullapi.response.ResponseHandler;
import org.example.restfullapi.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//принимает запросы и вызывает PatientService
@Slf4j
@RestController
@RequestMapping("v1/patients")
public class PatientController implements PatientControllerInt {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

   @Override
    public ResponseEntity<Object> list(@RequestBody PatientDTO patientDTO) {
        log.debug("Получен запрос на получение списка пациентов");
        return ResponseHandler.responseBuilder("Запрошенные данные успешно получены", HttpStatus.OK, patientService.list());
    }

    @Override
    public ResponseEntity<PatientDTO> add(@RequestBody PatientDTO patientDTO) {
        log.debug("Получен запрос на добавление пациента: {}", patientDTO);
        patientService.add(patientDTO);
        log.info("Пациент успешно добавлен");
        return new ResponseEntity<>(patientDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PatientDTO> delete(@PathVariable Long id) {
        log.debug("Получен запрос на удаление пациента с ID: {}", id);
        List<PatientDTO> patient = patientService.findById(id);
        if (patient.isEmpty()) {
            log.warn("Пациент с ID {} не найден", id);
            }
        patientService.delete(id);
        log.info("Пациент с ID {} успешно удален", id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PatientDTO> update(@RequestBody PatientDTO patientDTO) {
        log.debug("Получен запрос на обновление пациента: {}", patientDTO);
        patientService.update(patientDTO);
        log.info("Пациент успешно обновлен");
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }
}