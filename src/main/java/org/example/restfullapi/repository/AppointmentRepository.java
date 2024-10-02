package org.example.restfullapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.example.restfullapi.entity.Appointment;
import org.example.restfullapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

//Методы для сохранения, обновления и удаления записей на прием
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // Метод для получения всех записей на прием за определенный день
    @Query(value = "SELECT * FROM appointments  WHERE DATE(appointmentDate) = :date", nativeQuery = true)
    Page<Appointment> findByAppointmentDate(@Param("date") LocalDate date, Pageable pageable);

    // Метод для получения всех записей на прием для конкретного пациента
    Page<Appointment> findByPatient(Patient patient, Pageable pageable);

    // Метод для проверки доступности времени приема
    boolean existsByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);

    // Метод для подсчета количества записей на прием у конкретного пациента
    long countByPatient(Patient patient);

    // Нативный SQL-запрос для получения всех записей на прием, начиная с указанной даты
    @Query(value = "SELECT * FROM appointments WHERE appointment_date >= :startDate", nativeQuery = true)
        Page<Appointment> findAppointmentsFromDate(@Param("startDate") LocalDate startDate, Pageable pageable);

    // JPQL-запрос для получения всех записей на прием, начиная с указанной даты
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate >= :startDate")
    Page<Appointment> findAppointmentsFromDate1(@Param("startDate") LocalDate startDate, Pageable pageable);

    // HQL-запрос для получения всех записей на прием, начиная с указанной даты
    @Query("FROM Appointment a WHERE a.appointmentDate >= :startDate")
    Page<Appointment> findAppointmentsFromDate2(@Param("startDate") LocalDate startDate, Pageable pageable);

}