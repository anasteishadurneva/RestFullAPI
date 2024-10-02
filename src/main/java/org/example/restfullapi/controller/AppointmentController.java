package org.example.restfullapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.restfullapi.dto.AppointmentDTO;
import org.example.restfullapi.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Tag(name = "Appointments")
@RequestMapping("/v1/appointments")
public interface AppointmentController {



    @Operation(summary = "Создает новую запись на прием", tags = "Appointments")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Запись на прием успешно создана",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Неверный ввод")
    })
    @PostMapping
    AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointmentDTO);



    @Operation(summary = "Отменяет запись на прием по идентификатору", tags = "Appointments")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запись на прием успешно отменена",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @DeleteMapping("/{id}")
    void cancelAppointment(@PathVariable UUID id);



    @Operation(summary = "Получает записи на прием для пациента", tags = "Appointments")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Записи на прием успешно получены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AppointmentDTO.class)))
                    })
    })
    @GetMapping("/patient/{patientId}")
    Page<AppointmentDTO> getAppointmentsByPatient(@PathVariable Patient patientId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size);



    @Operation(summary = "Получает записи на прием по дате", tags = "Appointments")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Записи на прием успешно получены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AppointmentDTO.class)))
                    })
    })
    @GetMapping("/date/{date}")
    Page<AppointmentDTO> getAppointmentsByDate(@PathVariable LocalDate date,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size);



    @Operation(summary = "Получает количество записей на прием по идентификатору пациента", tags = "Appointments")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Количество записей на прием успешно получено",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Пациент не найден")
    })
    @GetMapping("/patient/count/{id}")
    long getAppointmentsCountByPatientId(@PathVariable Long id);



    @Operation(summary = "Получает записи на прием с указанной даты до сегодня", tags = "Appointments")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Записи на прием успешно получены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AppointmentDTO.class)))
                    })
    })
    @GetMapping("/date/from/{startDate}")
    Page<AppointmentDTO> getAppointmentsFromDateToToday(@PathVariable LocalDate startDate,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size);
}