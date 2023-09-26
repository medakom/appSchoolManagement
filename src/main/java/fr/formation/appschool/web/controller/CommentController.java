package fr.formation.appschool.web.controller;

import fr.formation.appschool.persistence.repository.CommentRepository;
import fr.formation.appschool.service.CommentService;
import fr.formation.appschool.web.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    @GetMapping("/comments")
    @ResponseBody
    public List<Comment> commentList(){
        return commentService.findAll();
    }

}
