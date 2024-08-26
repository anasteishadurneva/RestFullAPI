package org.example.restfullapi.controller;

import org.example.restfullapi.dto.PatientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface PatientControllerInt {
    @GetMapping(path = "list")
    ResponseEntity<Object> list(@RequestBody PatientDTO patientDTO);

    @PostMapping(path = "item")
    ResponseEntity<PatientDTO> add(@RequestBody PatientDTO patientDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<PatientDTO> delete(@PathVariable Long id);

    @PutMapping(path = "item")
    ResponseEntity<PatientDTO> update(@RequestBody PatientDTO patientDTO);
}
