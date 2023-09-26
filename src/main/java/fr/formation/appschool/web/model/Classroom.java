package fr.formation.appschool.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class Classroom {
    Integer id;
    String label;
    boolean availability;

     List<Course> courses;
}
