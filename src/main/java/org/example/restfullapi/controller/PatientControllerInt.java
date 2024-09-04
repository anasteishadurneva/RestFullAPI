package org.example.restfullapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.restfullapi.config.Patient;
import org.example.restfullapi.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Patients")
@RequestMapping("v1/patients")
public interface PatientControllerInt {

    @Operation(summary = "Принимает всех пациентов", tags = "Patients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Нашел пользователей",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PatientDTO.class)))
                    })
    })
    @GetMapping(path = "list")
    Page<PatientDTO> list(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size);

    @Operation(summary = "Ищет пациентов по id", tags = "Patients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Нашел пользователей",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PatientDTO.class)))
                    })
    })
    @GetMapping(path = "/{id}")
    PatientDTO getById(@PathVariable Long id);

    @Operation(summary = "Добавляет нового пациента", tags = "Patients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Пациент успешно добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PatientDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Неверный ввод")
    })
    @PostMapping(path = "item")
    PatientDTO add(@RequestBody PatientDTO patient);

    @Operation(summary = "Удаляет пациента по идентификатору", tags = "Patients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пациент успешно удален",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Пациент не найден")
    })
    @DeleteMapping("/{id}")
    PatientDTO delete(@PathVariable Long id);

    @Operation(summary = "Обновляет существующего пациента по частям", tags = "Patients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пациент обновлен успешно",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PatientDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Пациент не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный ввод")
    })
    @PatchMapping(path = "item")
    PatientDTO updateByParts(@RequestBody Patient patient);

    @Operation(summary = "Обновляет существующего пациента", tags = "Patients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пациент обновлен успешно",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PatientDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Пациент не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный ввод")
    })
    @PutMapping(path = "item")
    PatientDTO update(Patient patient);
}
