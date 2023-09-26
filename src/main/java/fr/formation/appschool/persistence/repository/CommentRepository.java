package fr.formation.appschool.persistence.repository;

import fr.formation.appschool.persistence.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
   @Query("SELECT comment FROM CommentEntity comment  JOIN FETCH comment.teacher teacher LEFT JOIN FETCH teacher.homeworks homeworks")
    List<CommentEntity> findAll();
    @Query("SELECT comment FROM CommentEntity comment INNER JOIN FETCH comment.teacher teacher  JOIN FETCH teacher.homeworks homeworks")
    Optional<CommentEntity> findById(Long id);
}
