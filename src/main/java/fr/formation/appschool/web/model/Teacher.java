package fr.formation.appschool.web.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Value
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Teacher extends Person{
   String subject;

   Course course;
    List<Homework> homeworks;
    List<Comment> comments ;
}
