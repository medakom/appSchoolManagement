package fr.formation.appschool.service;

import fr.formation.appschool.mapper.CourseMapper;
import fr.formation.appschool.mapper.TeacherMapper;
import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.model.TeacherEntity;
import fr.formation.appschool.persistence.repository.CourseRepository;
import fr.formation.appschool.persistence.repository.TeacherRepository;
import fr.formation.appschool.web.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherMapper teacherMapper;
    private final CourseMapper courseMapper;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public List<Teacher> findAll() {
        List<TeacherEntity> res = teacherRepository.findAll();


        return res.stream()
                .map(teacherMapper::fromTeacherEntity)
                .collect(toList());
    }

    public Teacher findOne(Integer id) {
        Optional<TeacherEntity> byId = teacherRepository.findById(id);
        return byId.map(teacherMapper::fromTeacherEntity).orElse(null);
    }


    public void save(Teacher  teacher){
        TeacherEntity entity = teacherMapper.toTeacherEntity(teacher);
        teacherRepository.save(entity);

}
public void deleteTeacher(Integer idTeacher){


      Optional <TeacherEntity>  teacherEntity = teacherRepository.findById(idTeacher);

      CourseEntity course = teacherEntity.get().getCourse();
   teacherRepository.delete(teacherEntity.get());
    if (course!=null && course.getTeachers().isEmpty()){
        courseRepository.delete(course);
    }





}

}

