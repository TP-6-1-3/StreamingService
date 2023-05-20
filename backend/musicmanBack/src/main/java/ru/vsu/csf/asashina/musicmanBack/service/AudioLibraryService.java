package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.NoSongInCollectionException;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongExistsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserWithSongsDTO;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

import java.util.List;

@Service
@AllArgsConstructor
public class AudioLibraryService {

    private final UserService userService;
    private final SongService songService;
    private final RecommendationService recommendationService;

    private final PageUtil pageUtil;

    public Page<SongDTO> getSongs(Long userId, Integer pageNumber, Integer size, String title) {
        UserWithSongsDTO userWithSongs = userService.getUserWithSongs(userId);
        long totalSize = userWithSongs.getSongs().size();
        List<SongDTO> songs = userWithSongs.getSongs().stream()
                .filter(song -> song.getTitle().toLowerCase().contains(title.toLowerCase()))
                .skip((long) (pageNumber - 1) * size)
                .limit(size)
                .toList();
        Page<SongDTO> pages = new PageImpl<>(songs, pageUtil.createPageRequest(pageNumber, size), totalSize);
        pageUtil.checkPageOutOfRange(pages, pageNumber);
        return pages;
    }

    public SongExistsDTO isSongInLibrary(Long userId, Long songId) {
        UserWithSongsDTO userWithSongs = userService.getUserWithSongs(userId);
        songService.getSongById(songId);
        return new SongExistsDTO(isSongInLibrary(userWithSongs, songId));
    }

    private boolean isSongInLibrary(UserWithSongsDTO userWithSongs, Long songId) {
        return userWithSongs.getSongs().stream().anyMatch(song -> song.getSongId().equals(songId));
    }

    @Transactional
    public void addSongToUsersLibrary(Long userId, Long songId) {
        UserWithSongsDTO userWithSongs = userService.getUserWithSongs(userId);
        SongDTO song = songService.getSongById(songId);
        if (isSongInLibrary(userWithSongs, songId)) {
            throw new EntityAlreadyExistsException("Песня уже есть в аудиотеке");
        }
        recommendationService.deleteFromRecommendation(userId, songId);
        userWithSongs.addSong(song);
        userService.updateUserWithSongs(userWithSongs);
    }

    @Transactional
    public void deleteSongFromUsersLibrary(Long userId, Long songId) {
        UserWithSongsDTO userWithSongs = userService.getUserWithSongs(userId);
        SongDTO song = songService.getSongById(songId);
        if (!isSongInLibrary(userWithSongs, songId)) {
            throw new NoSongInCollectionException("Песни нет в аудиотеке");
        }
        userWithSongs.deleteSong(song);
        userService.updateUserWithSongs(userWithSongs);
    }
}
