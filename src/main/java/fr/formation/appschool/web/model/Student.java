package fr.formation.appschool.web.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
@Getter
@Setter
public class Student extends Person{
   Double note;
   List<Course> courses;
   List<Comment> comments ;
}
