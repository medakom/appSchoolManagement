package fr.formation.appschool.service;

import fr.formation.appschool.mapper.StudentMapper;
import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.model.StudentEntity;
import fr.formation.appschool.persistence.repository.CourseRepository;
import fr.formation.appschool.persistence.repository.StudentRepository;
import fr.formation.appschool.web.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseRepository courseRepository;


    public List<Student> findAll() {
        List<StudentEntity> st = studentRepository.findAll();
        return st.stream()
                .map(studentMapper::fromStudentEntity)
                .collect(toList());
    }

    public Student findOne(Integer id) {
        Optional<StudentEntity> byId = studentRepository.findById(id);
        return byId.map(studentMapper::fromStudentEntity).orElse(null);
    }

    public Student addCourseToStudent(Integer studentId, Integer courseId) {
        StudentEntity student = studentRepository.findById(studentId).orElse(null);
        CourseEntity course = courseRepository.findById(courseId).orElse(null);
        student.getCourses().add(course);
        return studentMapper.fromStudentEntity(student);
    }

    public void save(Student student) {
        StudentEntity entity = studentMapper.toStudentEntity(student);
        studentRepository.save(entity);
    }
}
