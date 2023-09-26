package fr.formation.appschool.mapper;

import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.model.TeacherEntity;
import fr.formation.appschool.web.model.Course;
import fr.formation.appschool.web.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public Teacher fromTeacherEntity(TeacherEntity entity) {
        CourseEntity teacherCourse = entity.getCourse();
        Course course = toCourse(teacherCourse);
        Teacher teacher = Teacher.builder().id(entity.getId())
                .lastname(entity.getLastname())
                .firstname(entity.getFirstname())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .birthDate(entity.getBirthDate())
                .address(entity.getAddress())
                .subject(entity.getSubject())
                .course(course)
                .build();
        System.out.println(teacher);
        return teacher;
    }

    public TeacherEntity toTeacherEntity(Teacher teacher) {
        CourseEntity course = CourseEntity.builder()
                .id(teacher.getCourse().getId())
                .nameCourse(teacher.getCourse().getNameCourse())
                .build();

        TeacherEntity entity = TeacherEntity.builder()
                .id(teacher.getId())
                .lastname(teacher.getLastname())
                .firstname(teacher.getFirstname())
                .email(teacher.getEmail())
                .phone(teacher.getPhone())
                .birthDate(teacher.getBirthDate())
                .address(teacher.getAddress())
                .subject(teacher.getSubject())
                .course(course).build();
        return entity;
    }

    private  Course toCourse(CourseEntity teacherCourse) {
        return Course.builder().id(teacherCourse.getId())
                .nameCourse(teacherCourse.getNameCourse())
                .build();
    }
}
