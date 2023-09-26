package fr.formation.appschool.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name="teacher")
@SuperBuilder
@Value
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class TeacherEntity extends PersonEntity {
    String subject; //TODO

    @ManyToOne
    @JoinColumn(name="course_id")
    CourseEntity course;

    @OneToMany(mappedBy="teacher")
     List<HomeworkEntity> homeworks;

    @OneToMany(mappedBy = "teacher")
    List<CommentEntity> comments ;

}
