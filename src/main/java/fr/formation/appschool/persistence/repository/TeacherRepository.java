package fr.formation.appschool.persistence.repository;

import fr.formation.appschool.persistence.model.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {
   // @Query("SELECT teacher FROM TeacherEntity teacher JOIN FETCH teacher.course course")
    Optional<TeacherEntity> findById(@Param("id") Integer id);
    @Query("SELECT teacher FROM TeacherEntity teacher Left JOIN FETCH teacher.course course")
    List<TeacherEntity> findAll();

    Optional<TeacherEntity> findByEmail(String email);
}
