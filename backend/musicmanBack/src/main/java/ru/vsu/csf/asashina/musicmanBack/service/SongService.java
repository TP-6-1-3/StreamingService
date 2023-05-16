package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.mapper.SongMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongPageDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Song;
import ru.vsu.csf.asashina.musicmanBack.model.enumeration.SongSort;
import ru.vsu.csf.asashina.musicmanBack.repository.SongRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

@Service
@AllArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    private final PageUtil pageUtil;

    public Page<SongPageDTO> getAllSongs(
            Integer pageNumber,
            Integer size,
            String title,
            SongSort sort,
            Boolean isAsc,
            Long[] genreIds,
            Long singerId) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size, isAsc, sort.getQueryParam());
        Page<Song> songs = songRepository.getAll(singerId, genreIds, title, pageRequest);
        pageUtil.checkPageOutOfRange(songs, pageNumber);
        return songs.map(songMapper::toPageDTOFromEntity);
    }
}
