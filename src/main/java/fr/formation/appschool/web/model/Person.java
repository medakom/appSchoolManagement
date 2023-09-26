package fr.formation.appschool.web.model;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(force = true)
public abstract class Person {
    Integer id;
    String lastname;
    String firstname;
    String email;
    String phone;
    LocalDate birthDate;
    String address;
}
