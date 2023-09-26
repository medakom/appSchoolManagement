package fr.formation.appschool.service;

import fr.formation.appschool.mapper.FileDocumentMapper;
import fr.formation.appschool.persistence.model.ClassroomEntity;
import fr.formation.appschool.persistence.model.FileDocumentEntity;
import fr.formation.appschool.persistence.repository.FileDocumentRepository;
import fr.formation.appschool.web.model.Classroom;
import fr.formation.appschool.web.model.FileDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileDocumentService {
    private final FileDocumentRepository fileDocumentRepository;
    private final FileDocumentMapper fileDocumentMapper;

    public List<FileDocument> findAll() {
        List<FileDocumentEntity>  fileDocuments = fileDocumentRepository.findAll();
        return fileDocuments.stream()
                .map(fileDocumentMapper::fromFileDocumentEntity)
                .collect(Collectors.toList());
    }
    public FileDocument findOne(Long id) {
        Optional<FileDocumentEntity> byId = fileDocumentRepository.findById(id);
        return byId.map(fileDocumentMapper::fromFileDocumentEntity).orElse(null);
    }
    public void save(FileDocument fileDocument){

        FileDocumentEntity entity =fileDocumentMapper.toFileDocumentEntity(fileDocument);
       fileDocumentRepository.save(entity);


    }
}
