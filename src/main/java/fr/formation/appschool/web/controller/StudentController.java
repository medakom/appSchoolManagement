package fr.formation.appschool.web.controller;


import fr.formation.appschool.service.StudentService;
import fr.formation.appschool.web.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/students")
    @ResponseBody
    public List<Student> listStudents(){
        return studentService.findAll();
    }
}
