package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.mapper.PlaylistMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PlaylistDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PlaylistWithSongsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Playlist;
import ru.vsu.csf.asashina.musicmanBack.repository.PlaylistRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;

    private final UserService userService;

    private final PageUtil pageUtil;

    public Page<PlaylistDTO> getAll(String userEmail, Integer pageNumber, Integer size, String name) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck(userEmail);
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<Playlist> playlists = playlistRepository.getAll(user.getUserId(), name, pageRequest);
        pageUtil.checkPageOutOfRange(playlists, pageNumber);
        return playlists.map(playlistMapper::toDTOFromEntity);
    }

    public PlaylistWithSongsDTO getPlaylistById(String id, boolean isAdmin, Long userId) {
        Playlist playlist = findPlaylistById(id);
        if (!playlist.getUser().getUserId().equals(userId) || !isAdmin) {
            throw new AccessDeniedException("Нет доступа к плейлисту");
        }
        return playlistMapper.toDTOWithSongFromEntity(playlist);
    }

    private Playlist findPlaylistById(String id) {
        return playlistRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Плейлиста с данным ИД не существует"));
    }
}
