package fr.formation.appschool.mapper;

import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.model.StudentEntity;
import fr.formation.appschool.web.model.Course;
import fr.formation.appschool.web.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentMapper {
    public Student fromStudentEntity(StudentEntity entity) {

        List<Course> courses = toCourses(entity.getCourses());

        return Student.builder()
                .id(entity.getId())
                .lastname(entity.getLastname())
                .firstname(entity.getFirstname())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .birthDate(entity.getBirthDate())
                .address(entity.getAddress())
                .note(entity.getNote())
                .courses(courses)
                .build();
    }

     List<Course> toCourses(List<CourseEntity> courseList) {
        return courseList.stream().map(c->Course.builder()
                .id(c.getId())
                .nameCourse(c.getNameCourse())
                .build())
                .collect(Collectors.toList());
    }


    public StudentEntity toStudentEntity(Student student) {
        List<CourseEntity> courseEntityList = toCoursesEntity(student.getCourses());
        return StudentEntity.builder()
                .id(student.getId())
                .lastname(student.getLastname())
                .firstname(student.getFirstname())
                .email(student.getEmail())
                .phone(student.getPhone())
                .birthDate(student.getBirthDate())
                .address(student.getAddress())
                .note(student.getNote())
                .courses(courseEntityList)
                .build();
    }

    private List<CourseEntity> toCoursesEntity(List<Course> courseList) {
      return courseList.stream().map(c->CourseEntity.builder()
              .id(c.getId())
              .nameCourse(c.getNameCourse())
              .build())
              .collect(Collectors.toList());
    }
}
