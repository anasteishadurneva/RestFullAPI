package org.example.restfullapi.config;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Patient {
    @Id
    @SequenceGenerator(name = "patient_sequence", sequenceName = "patient_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence")
    private Long id;
    private String name;
    private LocalDate dob;
    private String gender;
    private String address;
    private String number;
    private String email;



    public int getAge(){
        return Period.between(dob,LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Patient{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", number='" + number + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}
