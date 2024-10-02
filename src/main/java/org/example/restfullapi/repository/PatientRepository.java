package org.example.restfullapi.repository;

import org.example.restfullapi.entity.Appointment;
import org.example.restfullapi.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAll(Pageable pageable);
    Page<Patient> findById(Long id, Pageable pageable);
}