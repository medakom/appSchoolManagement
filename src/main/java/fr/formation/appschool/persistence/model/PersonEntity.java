package fr.formation.appschool.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public  abstract class PersonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String lastname;
    String firstname;
    String email;
    String phone;

@Column(name = "birth_date")
    LocalDate birthDate;
     String address;
}
