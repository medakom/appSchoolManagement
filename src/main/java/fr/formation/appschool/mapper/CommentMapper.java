package fr.formation.appschool.mapper;

import fr.formation.appschool.persistence.model.CommentEntity;
import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.model.HomeworkEntity;
import fr.formation.appschool.persistence.model.TeacherEntity;
import fr.formation.appschool.web.model.Comment;
import fr.formation.appschool.web.model.Course;
import fr.formation.appschool.web.model.Homework;
import fr.formation.appschool.web.model.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    public Comment fromCommentEntity(CommentEntity entity){
        Teacher teacher = toTeacher(entity.getTeacher());
        return Comment.builder()
                .id(entity.getId())
                .text(entity.getText())
                .enable(entity.isEnable())
                .teacher(teacher)
                .build();
    }

    private Teacher toTeacher(TeacherEntity teacher) {
       List< HomeworkEntity> teacherHomework =teacher.getHomeworks();
       List<Homework>  homeworkList =toHomework(teacherHomework);
      Course course = toCourse(teacher.getCourse());
        return Teacher.builder()
                .id(teacher.getId())
                .lastname(teacher.getLastname())
                .firstname(teacher.getFirstname())
                .phone(teacher.getPhone())
                .birthDate(teacher.getBirthDate())
                .address(teacher.getAddress())
                .subject(teacher.getSubject())
                .course(course)
                .homeworks(homeworkList)
                .build();
    }

    private List<Homework> toHomework(List<HomeworkEntity> homeworkList ) {

        return homeworkList.stream().map(h->Homework.builder()
                        .id(h.getId())
                        .name(h.getName())
                        .description(h.getDescription())
                        .dueDate(h.getDueDate())

                .build())
                .collect(Collectors.toList());
    }

    private Course toCourse(CourseEntity course) {
        return Course.builder()
                .id(course.getId())
                .nameCourse(course.getNameCourse())
                .fileCourse(course.getFileCourse())
                .build();
    }

    public CommentEntity toCommentEntity(Comment comment) {
        Teacher teacher=comment.getTeacher();
        TeacherEntity teacherEntity =toTeacherEntity(teacher);
        return CommentEntity.builder()
                .id(comment.getId())
                .text(comment.getText())
                .enable(comment.isEnable())
                .teacher(teacherEntity)
                .build();
    }

    private TeacherEntity toTeacherEntity(Teacher teacher) {
        return TeacherEntity.builder()
                .id(teacher.getId())
                .lastname(teacher.getLastname())
                .firstname(teacher.getFirstname())
                .phone(teacher.getPhone())
                .birthDate(teacher.getBirthDate())
                .address(teacher.getAddress())
                .subject(teacher.getSubject())
                .build();
    }
}
