package fr.formation.appschool.persistence.repository;

import fr.formation.appschool.persistence.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    @Query("SELECT  student FROM StudentEntity student inner JOIN FETCH student.courses courses")
    Optional<StudentEntity> findById(@Param("id") Integer id);

    @Query("SELECT student FROM StudentEntity student Left JOIN FETCH student.courses courses")
    List<StudentEntity> findAll();
}
