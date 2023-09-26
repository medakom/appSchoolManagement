package fr.formation.appschool.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Value
@EqualsAndHashCode(of ="id")
@Table(name= "comment")
public class CommentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String text;
    boolean enable;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    TeacherEntity teacher;
    @ManyToOne
    @JoinColumn(name = "student_id")
    StudentEntity student;
}
