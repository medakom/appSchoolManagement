package fr.formation.appschool.web.controller;

import fr.formation.appschool.service.HomeworkService;
import fr.formation.appschool.web.model.Homework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeworkController {
    private  final HomeworkService homeworkService;
    public List<Homework> homeworkList(){
        return homeworkService.findAll();
    }
}
