package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.mapper.SingerMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SingerDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Singer;
import ru.vsu.csf.asashina.musicmanBack.repository.SingerRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

@Service
@AllArgsConstructor
public class SingerService {

    private final SingerRepository singerRepository;

    private final SingerMapper singerMapper;

    private final PageUtil pageUtil;

    public SingerDTO getSingerById(Long id) {
        return singerMapper.toDTOFromEntity(findSingerById(id));
    }

    private Singer findSingerById(Long id) {
        return singerRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Певец с заданным ИД не существует"));
    }

    public Page<SingerDTO> getAllSingers(Integer pageNumber, Integer size, String name, Boolean isAsc) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size, isAsc, "full_name");
        Page<Singer> singers = singerRepository.getAll(name, pageRequest);
        pageUtil.checkPageOutOfRange(singers, pageNumber);
        return singers.map(singerMapper::toDTOFromEntity);
    }
}
