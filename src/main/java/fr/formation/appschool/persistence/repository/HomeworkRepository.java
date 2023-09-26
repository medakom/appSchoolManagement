package fr.formation.appschool.persistence.repository;

import fr.formation.appschool.persistence.model.HomeworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<HomeworkEntity,Long> {
    @Query("SELECT  homework FROM HomeworkEntity homework LEFT JOIN FETCH homework.teacher teacher")
    List<HomeworkEntity> findAll();
}
