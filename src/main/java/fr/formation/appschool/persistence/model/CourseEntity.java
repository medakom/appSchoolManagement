package fr.formation.appschool.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
@Value
@Table(name = "course")
@EqualsAndHashCode(of ="id")

public class CourseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String nameCourse;
@Lob
byte[] fileCourse;
    @OneToMany(mappedBy = "course")
    List<TeacherEntity> teachers;
    @ManyToMany(mappedBy = "courses")

    List<StudentEntity> students;
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    ClassroomEntity classroom;
    @OneToMany(mappedBy = "course")
    List<FileDocumentEntity> fileDocuments;


}
