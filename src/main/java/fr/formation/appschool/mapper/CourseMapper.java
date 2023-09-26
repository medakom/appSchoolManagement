package fr.formation.appschool.mapper;

import fr.formation.appschool.persistence.model.*;
import fr.formation.appschool.web.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {
    public Course fromCourseEntity(CourseEntity entity){
      return Course.builder()
              .id(entity.getId())
              .nameCourse(entity.getNameCourse())
              .fileCourse(entity.getFileCourse())
              .teachers(toTeacher(entity.getTeachers()))
              .build();
    }

    private List<FileDocument> toFileDocument(List<FileDocumentEntity> fileDocuments) {
        return fileDocuments.stream().map(fd->FileDocument.builder()
                .id(fd.getId())
                .name(fd.getName())
                .description(fd.getDescription())
                .type(fd.getType())
                .content(fd.getContent())
                .build()).collect(Collectors.toList());
    }


    private Classroom toClassroom(ClassroomEntity classroom) {
       return Classroom.builder()
               .id(classroom.getId())
               .label(classroom.getLabel())
               .availability(classroom.isAvailability())
               .build();
    }

    private List<Student> toStudent(List<StudentEntity> students) {
      return   students.stream().map(s->Student.builder()
                .id(s.getId())
                .lastname(s.getLastname())
                .firstname(s.getFirstname())
                .phone(s.getPhone())
                .birthDate(s.getBirthDate())
                .address(s.getAddress())
                .note(s.getNote())
                .build()).collect(Collectors.toList());
    }

    private List<Teacher> toTeacher(List<TeacherEntity> teachers) {
      return   teachers.stream().map(t->Teacher.builder()
                .id(t.getId())
                .lastname(t.getLastname())
                .firstname(t.getFirstname())
                .phone(t.getPhone())
                .birthDate(t.getBirthDate())
                .address(t.getAddress())
                .subject(t.getSubject()).build())
                .collect(Collectors.toList());

    }

    public CourseEntity toCourseEntity(Course course) {
        List<TeacherEntity> teacherEntityList = toTeacherEntity(course.getTeachers());
       // List<StudentEntity> studentEntityList = toStudentEntity(course.getStudents());
     return    CourseEntity.builder()
                .id(course.getId())
                .nameCourse(course.getNameCourse())
                .fileCourse(course.getFileCourse())
            // .students(studentEntityList)
             .teachers(teacherEntityList)

                .build();
    }

    private List<TeacherEntity> toTeacherEntity(List<Teacher> teachers) {
     return    teachers.stream().map(teacher -> TeacherEntity.builder()
             .id(teacher.getId())
                .lastname(teacher.getLastname())
             .firstname(teacher.getFirstname())
             .phone(teacher.getPhone())
             .birthDate(teacher.getBirthDate())
             .address(teacher.getAddress())
             .subject(teacher.getSubject())
             .homeworks(toHomeworkEntity(teacher.getHomeworks()))
             .build())
             .collect(Collectors.toList());

    }

    private List<HomeworkEntity> toHomeworkEntity(List<Homework> homeworks) {
        return homeworks.stream().map( h -> HomeworkEntity.builder()
                        .id(h.getId())
                        .name(h.getName())
                        .description(h.getDescription())
                        .dueDate(h.getDueDate())
                .build())
                .collect(Collectors.toList());
    }

    private List<Homework> toHomework(List<HomeworkEntity> homeworkEntityList) {
       return homeworkEntityList.stream().map(h->Homework.builder()
                       .id(h.getId())
                       .name(h.getName())
                       .dueDate(h.getDueDate())

               .build())
               .collect(Collectors.toList());
    }


    private List<StudentEntity> toStudentEntity(List<Student> students) {
       return students.stream().map(student -> StudentEntity.builder()
               .id(student.getId())
               .lastname(student.getLastname())
               .firstname(student.getFirstname())
               .phone(student.getPhone())
               .birthDate(student.getBirthDate())
               .address(student.getAddress())
               .note(student.getNote())
               .build())
               .collect(Collectors.toList());
    }

    public Course fromCourseAndClassroomEntity(CourseEntity courseEntity) {
        return Course.builder()
                .id(courseEntity.getId())
                .nameCourse(courseEntity.getNameCourse())
                .fileCourse(courseEntity.getFileCourse())
                .classroom(toClassroom(courseEntity.getClassroom()))
                .build();

    }

    public Course fromCourseAndStudentEntity(CourseEntity courseEntity) {
        return Course.builder().id(courseEntity.getId())
                .nameCourse(courseEntity.getNameCourse())
                .fileCourse(courseEntity.getFileCourse())
                .students(toStudent(courseEntity.getStudents()))
                .build();

    }
}
