package fr.formation.appschool.mapper;

import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.model.HomeworkEntity;
import fr.formation.appschool.persistence.model.TeacherEntity;
import fr.formation.appschool.web.model.Course;
import fr.formation.appschool.web.model.Homework;
import fr.formation.appschool.web.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class HomeworkMapper {
    public Homework fromHomeworkEntity(HomeworkEntity entity) {
        CourseEntity courseEntity = entity.getTeacher().getCourse();
        Course course = toCourse(courseEntity);
//
        TeacherEntity teacherEntity = entity.getTeacher();

        Teacher teacher = Teacher.builder().id(teacherEntity.getId())
                .firstname(teacherEntity.getFirstname())
                .lastname(teacherEntity.getLastname())
                .phone(teacherEntity.getPhone())
                .birthDate(teacherEntity.getBirthDate())
                .address(teacherEntity.getAddress())
                .subject(teacherEntity.getSubject())
                .course(course)
                .build();
        Homework homework = Homework.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .dueDate(entity.getDueDate())
                .teacher(teacher)
                .build();
        return homework;
    }

    private Course toCourse(CourseEntity courseEntity) {
        return Course.builder()
                .id(courseEntity.getId())
                .nameCourse(courseEntity.getNameCourse())
                .build();
    }

    private CourseEntity toCourseEntity(Course course) {
        return CourseEntity.builder()
                .id(course.getId())
                .nameCourse(course.getNameCourse())
                .build();
    }

    public TeacherEntity toTeacherEntity(Teacher teacher) {
        Course course = teacher.getCourse();
        CourseEntity entity = toCourseEntity(course);
        return TeacherEntity.builder()
                .id(teacher.getId())
                .lastname(teacher.getLastname())
                .firstname(teacher.getFirstname())
                .phone(teacher.getPhone())
                .birthDate(teacher.getBirthDate())
                .address(teacher.getAddress())
                .subject(teacher.getSubject())
                .course(entity)
                .build();
    }

    public HomeworkEntity toHomeworkEntity(Homework homework) {
        TeacherEntity teacherEntity = toTeacherEntity(homework.getTeacher());

        return HomeworkEntity.builder()
                .id(homework.getId())
                .name(homework.getName())
                .description(homework.getDescription())
                .dueDate(homework.getDueDate())
                .teacher(teacherEntity)
                .build();
    }

}
//    public Teacher toTeacher(TeacherEntity teacherEntity,HomeworkEntity entity){
//       CourseEntity courseEntity1 = entity.getTeacher().getCourse();
//       Course course =  Course.builder()
//               .id(courseEntity1.getId())
//               .nameCourse(courseEntity1.getNameCourse())
//               .build();
//    return Teacher.builder().id(teacherEntity.getId())
//            .firstname(teacherEntity.getFirstname())
//            .lastname(teacherEntity.getLastname())
//            .subject(teacherEntity.getSubject())
//            .course(course)
//                .build();
//}}
