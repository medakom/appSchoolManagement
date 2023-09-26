package fr.formation.appschool.web.controller;

import fr.formation.appschool.service.ClassroomService;
import fr.formation.appschool.web.model.Classroom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;
    public List<Classroom> classroomList(){
        return classroomService.findAll();
    }

}
