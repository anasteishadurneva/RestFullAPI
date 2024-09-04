package org.example.restfullapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientDTO {
     String name;
     LocalDate dob;
     String gender;
     String address;
     String number;
     String email;

}