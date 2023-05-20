package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
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
import ru.vsu.csf.asashina.musicmanBack.model.request.CreatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.repository.PlaylistRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;
import ru.vsu.csf.asashina.musicmanBack.utils.UuidUtil;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;

    private final PageUtil pageUtil;

    public Page<PlaylistDTO> getAll(Long userId, Integer pageNumber, Integer size, String name) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<Playlist> playlists = playlistRepository.getAll(userId, name, pageRequest);
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

    @Transactional
    public void createPlaylist(CreatePlaylistRequest request, UserDTO user) {
        Playlist playlist = playlistMapper.toEntityFromCreateRequest(
                UuidUtil.generateRandomUUIDInString(), request, user);
        playlistRepository.save(playlist);
    }
}
