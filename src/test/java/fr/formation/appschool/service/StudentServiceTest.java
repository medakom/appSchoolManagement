package fr.formation.appschool.service;

import fr.formation.appschool.persistence.repository.StudentRepository;
import fr.formation.appschool.web.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@ActiveProfiles("it")
class StudentServiceTest {

    @Autowired
    StudentService studentService;

    @Test
    @Sql(statements = "INSERT INTO student(id, lastname,firstname) values(1, 'AKA','Abdel') ")
    void should_get_all_when_exists() {
        List<Student> all = studentService.findAll();

        Assertions.assertThat(all).isNotEmpty();
    }

}