package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongExistsDTO;

@Service
@AllArgsConstructor
public class AudioLibraryService {

    private final SongService songService;
    private final RecommendationService recommendationService;

    @Transactional
    public Page<SongDTO> getSongs(Long userId, Integer pageNumber, Integer size, String title) {
        return songService.getUsersSongs(userId, pageNumber, size, title);
    }

    @Transactional
    public SongExistsDTO isSongInLibrary(Long userId, Long songId) {
        return new SongExistsDTO(songService.isSongInUsersLibrary(songId, userId));
    }

    @Transactional
    public void addSongToUsersLibrary(Long userId, Long songId) {
        songService.addSongToUsersLibrary(songId, userId);
        recommendationService.deleteFromRecommendation(userId, songId);
    }

    @Transactional
    public void deleteSongFromUsersLibrary(Long userId, Long songId) {
        songService.deleteSongFromUsersLibrary(songId, userId);
    }
}
