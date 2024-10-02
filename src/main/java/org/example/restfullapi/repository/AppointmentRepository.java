package org.example.restfullapi.repository;

import org.example.restfullapi.entity.Appointment;
import org.example.restfullapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//Методы для сохранения, обновления и удаления записей на прием
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    // Метод для получения всех записей на прием, которые начинаются в определенный день
    List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    // Метод для получения количества записей на прием у конкретного пациента
    long countByPatient(Patient patient);
    // Метод для проверки доступности времени приема
    boolean existsByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);
    // Метод для получения всех записей на прием для конкретного пациента
    List<Appointment> findByPatientId(Long id);
}