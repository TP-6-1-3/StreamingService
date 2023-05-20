package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongPageDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UsersSongDTO;

@Service
@AllArgsConstructor
public class AudioLibraryService {

    private final UserService userService;
    private final SongService songService;

    public Page<SongPageDTO> getAllSongs(String userEmail, Integer pageNumber, Integer size, String title) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck(userEmail);
        return songService.getUsersSongs(user.getUserId(), pageNumber, size, title);
    }

    @Transactional
    public void addSong(String userEmail, Long songId) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck(userEmail);
        songService.addSongToUsersLibrary(user.getUserId(), songId);
    }

    public UsersSongDTO doesSongExists(String userEmail, Long songId) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck(userEmail);
        return new UsersSongDTO(songService.isSongInUsersLibrary(user.getUserId(), songId));
    }

    @Transactional
    public void deleteSong(String userEmail, Long songId) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck(userEmail);
        songService.deleteSongFromUsersLibrary(user.getUserId(), songId);
    }
}
