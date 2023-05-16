package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.mapper.GenreMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Genre;
import ru.vsu.csf.asashina.musicmanBack.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    public Set<GenreDTO> getGenresByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Genre> genres = genreRepository.findAllByGenreIdIn(ids);
        if (genres.size() < ids.size()) {
            throw new EntityDoesNotExistException("Жанров с заданными ИД не существует");
        }
        return genreMapper.toDTOFromEntitySet(genres);
    }
}
