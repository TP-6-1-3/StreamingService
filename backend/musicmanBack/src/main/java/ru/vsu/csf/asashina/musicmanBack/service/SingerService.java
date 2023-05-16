package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.mapper.SingerMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SingerDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Singer;
import ru.vsu.csf.asashina.musicmanBack.repository.SingerRepository;

@Service
@AllArgsConstructor
public class SingerService {

    private final SingerRepository singerRepository;

    private final SingerMapper singerMapper;

    public SingerDTO getSingerById(Long id) {
        return singerMapper.toDTOFromEntity(findSingerById(id));
    }

    private Singer findSingerById(Long id) {
        return singerRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Певец с заданным ИД не существует"));
    }
}
