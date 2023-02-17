package fr.formation.appschool.mapper;

import fr.formation.appschool.persistence.model.StudentEntity;
import fr.formation.appschool.web.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {


    public Student fromStudentEntity(StudentEntity entity) {
        return Student.builder()
                .id(entity.getId())
                .lastname(entity.getLastname())
                .firstname(entity.getFirstname())
                .build();
    }
}
