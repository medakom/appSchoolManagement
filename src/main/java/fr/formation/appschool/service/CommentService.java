package fr.formation.appschool.service;

import fr.formation.appschool.mapper.CommentMapper;
import fr.formation.appschool.mapper.StudentMapper;
import fr.formation.appschool.mapper.TeacherMapper;
import fr.formation.appschool.persistence.model.CommentEntity;
import fr.formation.appschool.persistence.repository.CommentRepository;
import fr.formation.appschool.persistence.repository.StudentRepository;
import fr.formation.appschool.persistence.repository.TeacherRepository;
import fr.formation.appschool.web.model.Comment;
import fr.formation.appschool.web.model.Student;
import fr.formation.appschool.web.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final JavaMailSender javaMailSender;
    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    public List<Comment> findAll(){
        List<CommentEntity> commentEntityList = commentRepository.findAll();
        return commentEntityList.stream()
                .map(commentMapper::fromCommentEntity)
                .collect(Collectors.toList());
    }
    public Comment findOne(Long id) {
        Optional<CommentEntity> byId = commentRepository.findById(id);
        return byId.map(commentMapper::fromCommentEntity).orElse(null);
    }
    public void save(Comment comment){

        CommentEntity entity = commentMapper.toCommentEntity(comment);
       commentRepository.save(entity);


    }
    public void sendComment(Integer teacherId, Integer studentId, String text){
        Teacher teacher = teacherMapper.fromTeacherEntity(teacherRepository.findById(teacherId).orElse(null));
        Student student = studentMapper.fromStudentEntity(studentRepository.findById(studentId).orElse(null));
        CommentEntity commentEntity = commentMapper.toCommentEntity(Comment.builder()
                .teacher(teacher)
                .student(student)
                .text(text)
                .build());
        commentRepository.save(commentEntity);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(teacher.getEmail());
        message.setTo(student.getEmail());
        message.setSubject("New comment from your teacher");
        message.setText("Dear " + student.getLastname() + " " + student.getFirstname() + ",\n\n" +
                "Your teacher " + teacher.getLastname() + " " +teacher.getFirstname() + ",\n\n" +" has left a new comment for you:\n\n" +
                text + "\n\n" +
                "Regards,\n" +
                "Your school");
        javaMailSender.send(message);
    }
}
