package fr.formation.appschool.web.controller;

import fr.formation.appschool.service.TeacherService;
import fr.formation.appschool.web.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    @GetMapping("/teachers")
    @ResponseBody
    public List<Teacher> teacherList(){
        return teacherService.findAll();
    }

}
