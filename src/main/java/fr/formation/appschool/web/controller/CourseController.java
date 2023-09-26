package fr.formation.appschool.web.controller;

import fr.formation.appschool.service.CourseService;
import fr.formation.appschool.web.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private  final CourseService courseService;
    @GetMapping("/courses")
    @ResponseBody
    public List<Course> listCourses(){
        return courseService.findAll();
    }
}
