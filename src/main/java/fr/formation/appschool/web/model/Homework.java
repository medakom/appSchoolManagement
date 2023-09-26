package fr.formation.appschool.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class Homework {
    Long id;

    String name;

    String description;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dueDate;


     Teacher teacher;
}
