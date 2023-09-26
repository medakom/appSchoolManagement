package fr.formation.appschool.service;

import fr.formation.appschool.mapper.ClassroomMapper;
import fr.formation.appschool.persistence.model.ClassroomEntity;
import fr.formation.appschool.persistence.repository.ClassroomRepository;
import fr.formation.appschool.web.model.Classroom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;
    public List<Classroom> findAll() {
        List<ClassroomEntity> res = classroomRepository.findAll();


        return res.stream()
                .map(classroomMapper::fromClassroomEntity)
                .collect(toList());
    }
    public Classroom findOne(Integer id) {
        Optional<ClassroomEntity> byId = classroomRepository.findById(id);
        return byId.map(classroomMapper::fromClassroomEntity).orElse(null);
    }
    public void save(Classroom classroom){

    ClassroomEntity entity = classroomMapper.toClassroomEntity(classroom);
    classroomRepository.save(entity);


    }
}
