package fr.formation.appschool.service;

import fr.formation.appschool.mapper.HomeworkMapper;
import fr.formation.appschool.persistence.model.HomeworkEntity;
import fr.formation.appschool.persistence.repository.HomeworkRepository;
import fr.formation.appschool.web.model.Homework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final HomeworkMapper homeworkMapper;

    public List<Homework> findAll() {
        List<HomeworkEntity> res = homeworkRepository.findAll();


        return res.stream()
                .map(homeworkMapper::fromHomeworkEntity)
                .collect(toList());
    }

    public Homework findOne(Long id) {
        Optional<HomeworkEntity> byId = homeworkRepository.findById(id);
        return byId.map(homeworkMapper::fromHomeworkEntity).orElse(null);
    }

    public void save(Homework homework) {

        HomeworkEntity entity = homeworkMapper.toHomeworkEntity(homework);
        homeworkRepository.save(entity);
    }
}