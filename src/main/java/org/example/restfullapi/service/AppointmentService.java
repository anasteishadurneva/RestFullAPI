package org.example.restfullapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restfullapi.exception.AppointmentNotFoundException;
import org.example.restfullapi.exception.PatientNotFoundException;
import org.example.restfullapi.mapper.AppointmentMapper;
import org.example.restfullapi.entity.Appointment;
import org.example.restfullapi.entity.AppointmentStatus;
import org.example.restfullapi.dto.AppointmentDTO;
import org.example.restfullapi.entity.Patient;
import org.example.restfullapi.repository.AppointmentRepository;
import org.example.restfullapi.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        log.info("Создание новой записи на прием: {}", appointmentDTO);

        // Проверка на наличие пациента
        if (appointmentDTO.getPatient() == null || appointmentDTO.getPatient().getId() == null) {
            log.error("Идентификатор пациента не указан в DTO");
            throw new RuntimeException("Идентификатор пациента не указан");
        }

        Patient patient = patientRepository.findById(appointmentDTO.getPatient().getId())
                .orElseThrow(() -> new PatientNotFoundException("Пациент не найден с ID: " + appointmentDTO.getPatient().getId()));

        // Длительность приема стандартная – 15 минут
        LocalDateTime start = appointmentDTO.getAppointmentDate();
        LocalDateTime end = start.plusMinutes(15); // Конец приема через 15 минут

        // Проверка доступности времени
        if (appointmentRepository.existsByAppointmentDateBetween(start, end)) {
            log.error("Время уже занято для записи на прием с {} до {}", start, end);
            throw new RuntimeException("Время уже занято");
        }

        // Создание новой записи на прием
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setPatient(patient); // Установка пациента
        appointment.setStatus(AppointmentStatus.SCHEDULED); // Установка статуса "ЗАПЛАНИРОВАНО"

        // Сохранение записи в базе данных
        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Запись на прием успешно создана с идентификатором: {}", savedAppointment.getId());

        return appointmentMapper.toDTO(savedAppointment); // Преобразование в DTO для возврата
    }

    @Transactional
    public void cancelAppointment(UUID appointmentId) {
        log.info("Отмена записи на прием с идентификатором: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Запись на прием не найдена с ID: " + appointmentId));

        appointment.setStatus(AppointmentStatus.CANCELLED); // Установка статуса "ОТМЕНЕНО"
        appointmentRepository.save(appointment);
    }


    public Page<AppointmentDTO> getAppointmentsByPatient(Patient patient, int page,int size) {
        log.info("Получение всех записей на прием для пациента: {}", patient.getId());
        Pageable pageable = PageRequest.of(page, size);
        Page<Appointment> appointments = appointmentRepository.findByPatient(patient, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> getAppointmentsByDate(LocalDate date, int page,int size) {
        log.info("Получение всех записей на прием для даты: {}", date);
        Pageable pageable = PageRequest.of(page, size);
        Page<Appointment> appointments = appointmentRepository.findByAppointmentDate(date, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public long countAppointmentsForPatient(Long patientId) {
        log.info("Получение количества записей на прием для пациента с ID: {}", patientId);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Пациент не найден с ID: " + patientId));

        return appointmentRepository.countByPatient(patient);
    }

    public Page<AppointmentDTO> getAppointmentsFromDateToToday(LocalDate startDate, int page,int size) {
        log.info("Получение всех записей на прием с даты: {} до сегодня", startDate);
        Pageable pageable = PageRequest.of(page, size);
        Page<Appointment> appointments = appointmentRepository.findAppointmentsFromDate(startDate, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }
}