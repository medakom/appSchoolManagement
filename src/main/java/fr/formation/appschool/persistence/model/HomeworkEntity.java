package fr.formation.appschool.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
@Entity
@Builder
@Getter
@Setter
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "homework")

public class HomeworkEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

     String name;

    String description;


@Column(name = "due_date")
     LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
     TeacherEntity teacher;
}
