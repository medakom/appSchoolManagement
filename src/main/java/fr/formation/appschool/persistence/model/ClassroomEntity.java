package fr.formation.appschool.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(force = true)
@Value
@Table(name = "classroom")
public class ClassroomEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String label;
    boolean availability;
    @OneToMany(mappedBy = "classroom")
    List<CourseEntity> courses;

}
