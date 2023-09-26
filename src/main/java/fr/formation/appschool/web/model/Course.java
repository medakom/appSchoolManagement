package fr.formation.appschool.web.model;

import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Value
@Getter
@Setter
@Table(name = "course")
public class Course {
    Integer id;
    String nameCourse;
    byte[] fileCourse;


    List<Teacher> teachers;


     List<Student> students ;
    Classroom classroom;
    List<FileDocument> fileDocuments;
}
