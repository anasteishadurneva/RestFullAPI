package org.example.restfullapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restfullapi.config.AppointmentMapper;
import org.example.restfullapi.entity.Appointment;
import org.example.restfullapi.entity.AppointmentStatus;
import org.example.restfullapi.dto.AppointmentDTO;
import org.example.restfullapi.entity.Patient;
import org.example.restfullapi.repository.AppointmentRepository;
import org.example.restfullapi.repository.PatientRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
                .orElseThrow(() -> new RuntimeException("Пациент не найден с ID: " + appointmentDTO.getPatient().getId()));

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

        Appointment appointment = null;
        try {
            appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        appointment.setStatus(AppointmentStatus.CANCELLED); // Установка статуса "ОТМЕНЕНО"
        appointmentRepository.save(appointment);
    }

    public List<AppointmentDTO> getAppointmentsByPatient(Long patientId) {
        log.info("Получение всех записей на прием для пациента с идентификатором: {}", patientId);

        // Получение записей на прием для конкретного пациента
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        // Преобразование списка записей в список DTO с использованием toDTOList
        return appointmentMapper.toDTOList(appointments);
    }

    public List<AppointmentDTO> getAppointmentsByDate(LocalDateTime date) {
        log.info("Получение всех записей на прием для даты: {}", date);

        // Получаем начало и конец дня для фильтрации записей
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        // Получение записей на прием в указанный день
        List<Appointment> appointments = appointmentRepository.findByAppointmentDateBetween(startOfDay,endOfDay);

        // Преобразование списка записей в список DTO с использованием toDTOList
        return appointmentMapper.toDTOList(appointments);
    }
}