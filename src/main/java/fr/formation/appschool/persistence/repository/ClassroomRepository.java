package fr.formation.appschool.persistence.repository;

import fr.formation.appschool.persistence.model.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity,Integer> {

    @Query("SELECT  classroom FROM ClassroomEntity classroom LEFT JOIN FETCH classroom.courses courses")
    List<ClassroomEntity> findAll();
   @Query( "select classroom from ClassroomEntity classroom inner JOIN FETCH classroom.courses courses")
   Optional<ClassroomEntity> findById( Integer id);

}
