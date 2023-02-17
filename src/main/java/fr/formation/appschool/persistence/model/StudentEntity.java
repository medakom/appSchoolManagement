package fr.formation.appschool.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Entity
@Builder
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "student")
public class StudentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String firstname;

    String lastname;

}
