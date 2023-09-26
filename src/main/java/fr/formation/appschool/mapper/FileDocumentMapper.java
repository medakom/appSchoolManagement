package fr.formation.appschool.mapper;

import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.model.FileDocumentEntity;
import fr.formation.appschool.web.model.Course;
import fr.formation.appschool.web.model.FileDocument;
import org.springframework.stereotype.Component;

@Component

public class FileDocumentMapper {
    public FileDocument fromFileDocumentEntity (FileDocumentEntity entity){
       CourseEntity course = entity.getCourse();
       Course course1 = toCourse(course);
        return FileDocument.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .content(entity.getContent())
                .course(course1)
                .build();
    }

    private Course toCourse(CourseEntity course) {
        return Course.builder()
                .id(course.getId())
                .nameCourse(course.getNameCourse())
                .fileCourse(course.getFileCourse())
                .build();
    }


    public FileDocumentEntity toFileDocumentEntity(FileDocument fileDocument) {
        Course course = fileDocument.getCourse();
        CourseEntity courseEntity = toCourseEntity(course);
        return FileDocumentEntity.builder()
                .id(fileDocument.getId())
                .name(fileDocument.getName())
                .description(fileDocument.getDescription())
                .type(fileDocument.getType())
                .content(fileDocument.getContent())
                .course(courseEntity)
                .build();
    }

    private CourseEntity toCourseEntity(Course course) {
        return CourseEntity.builder()
                .id(course.getId())
                .nameCourse(course.getNameCourse())
                .fileCourse(course.getFileCourse())
                .build();
    }


}
