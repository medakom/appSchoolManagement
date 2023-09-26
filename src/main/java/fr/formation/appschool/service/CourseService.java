package fr.formation.appschool.service;

import fr.formation.appschool.mapper.CourseMapper;
import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.repository.CourseRepository;
import fr.formation.appschool.web.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<Course> findAll() {
        List<CourseEntity> courses = courseRepository.findAll();
        return courses.stream()
                .map(courseMapper::fromCourseEntity)
                .collect(Collectors.toList());
    }
    public Course findOneCourseAndClassroom(Integer id) {
        Optional<CourseEntity> byId = courseRepository.findById(id);
        return byId.map(courseMapper::fromCourseAndClassroomEntity).orElse(null);
    }

    public Course findOne(Integer id) {
        Optional<CourseEntity> byId = courseRepository.findById(id);
        return byId.map(courseMapper::fromCourseEntity).orElse(null);
    }
    public Course findOneCourseAndStudent(Integer id) {
       Optional<CourseEntity>byId = courseRepository.findCourseEntityById(id);
        return byId.map(courseMapper::fromCourseAndStudentEntity).orElse(null);
    }
    public void save(Course course){
        CourseEntity entity = courseMapper.toCourseEntity(course);
        courseRepository.save(entity);




}}