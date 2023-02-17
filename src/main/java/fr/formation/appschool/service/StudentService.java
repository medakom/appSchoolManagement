package fr.formation.appschool.service;

import fr.formation.appschool.mapper.StudentMapper;
import fr.formation.appschool.persistence.model.StudentEntity;
import fr.formation.appschool.persistence.repository.StudentRepository;
import fr.formation.appschool.web.model.Student;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<Student> findAll() {
        List<StudentEntity> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::fromStudentEntity)
                .collect(toList());
    }
}
