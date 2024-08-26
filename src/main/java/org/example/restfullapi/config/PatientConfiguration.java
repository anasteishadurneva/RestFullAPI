package org.example.restfullapi.config;

import org.example.restfullapi.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class PatientConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            List<Patient> patients = new ArrayList<>();
            patients.add(new Patient(1L, "Ana", LocalDate.of(2005, 9, 10), "Ж", "г. Москва, д.4", "+7(925)760-40-60", "andfgheva@yandex.ru"));
            patients.add(new Patient(2L, "Max", LocalDate.of(2001, 5, 11), "M", "г. Москва, д.3", "+7(950)761-14-15", "dfjhhevgh@yandex.ru"));

            // Проверяем, есть ли уже пациенты в базе данных
            if (patientRepository.count() == 0) {
                patientRepository.saveAll(patients);
                System.out.println("Тестовые данные пациентов успешно сохранены.");
            } else {
                System.out.println("Тестовые данные пациентов уже существуют в базе данных.");
            }
        };
    }
}