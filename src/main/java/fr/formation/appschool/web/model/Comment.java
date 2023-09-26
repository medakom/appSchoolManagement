package fr.formation.appschool.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class Comment {
    Long id;
    String text;
    boolean enable;

    Teacher teacher;
   Student student;
}
