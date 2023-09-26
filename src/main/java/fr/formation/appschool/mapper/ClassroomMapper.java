package fr.formation.appschool.mapper;

import fr.formation.appschool.persistence.model.ClassroomEntity;
import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.web.model.Classroom;
import fr.formation.appschool.web.model.Course;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassroomMapper {
    public Classroom fromClassroomEntity(ClassroomEntity entity){
        List<Course> courses = toCourses(entity.getCourses());

        return Classroom.builder().id(entity.getId())
                .availability(entity.isAvailability())
                .label(entity.getLabel())
                .courses(courses)
                .build();
    }
    List<Course> toCourses(List<CourseEntity> courseList) {
        return courseList.stream().map(c->Course.builder()
                        .id(c.getId())
                        .nameCourse(c.getNameCourse())
                        .fileCourse(c.getFileCourse())
                        .build())
                .collect(Collectors.toList());
    }

    public ClassroomEntity toClassroomEntity(Classroom classroom) {

        List<CourseEntity> courseEntityList = toCoursesEntity(classroom.getCourses());

        return ClassroomEntity.builder()
                .id(classroom.getId())
                .label(classroom.getLabel())
                .availability(classroom.isAvailability())
                .courses(courseEntityList)
                .build();
    }
    List<CourseEntity> toCoursesEntity(List<Course> courseEntityList){
        return courseEntityList.stream().map(c->CourseEntity.builder()
                        .id(c.getId())
                        .nameCourse(c.getNameCourse())
                        .build())
                .collect(Collectors.toList());

    }
}
