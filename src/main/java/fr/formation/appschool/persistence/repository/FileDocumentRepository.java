package fr.formation.appschool.persistence.repository;

import fr.formation.appschool.persistence.model.FileDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileDocumentRepository extends JpaRepository<FileDocumentEntity, Long> {
    @Query("SELECT  fileDocument FROM FileDocumentEntity fileDocument LEFT JOIN FETCH fileDocument.course course")
    List<FileDocumentEntity> findAll();
}
