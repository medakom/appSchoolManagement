package fr.formation.appschool.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Value
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "student")
public class StudentEntity extends PersonEntity {
    Double note;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_student", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    List<CourseEntity> courses ;
    @OneToMany(mappedBy = "student")
    List<CommentEntity> comments ;

}
