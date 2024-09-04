package org.example.restfullapi.config;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;
    @Column(name = "patient_name")
    private String name;
    @Column(name = "patient_dob")
    private LocalDate dob;
    @Column(name = "patient_gender")
    private String gender;
    @Column(name = "patient_address")
    private String address;
    @Column(name = "patients_number")
    private String number;
    @Column(name = "patient_email")
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
