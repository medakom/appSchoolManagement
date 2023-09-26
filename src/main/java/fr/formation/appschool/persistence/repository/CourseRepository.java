package fr.formation.appschool.persistence.repository;

import fr.formation.appschool.persistence.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity,Integer> {
 @Query("SELECT course FROM CourseEntity course LEFT JOIN FETCH course.teachers teachers")
    List<CourseEntity> findAll();

    @Query("SELECT course FROM CourseEntity course INNER JOIN FETCH course.teachers teachers")
    Optional<CourseEntity> findById(Integer id);
    @Query("SELECT course FROM CourseEntity course LEFT JOIN FETCH course.students students")
 Optional<CourseEntity> findCourseEntityById(Integer id);
}
