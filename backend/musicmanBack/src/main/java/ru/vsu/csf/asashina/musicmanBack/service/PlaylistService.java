package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.exception.NoSongInCollectionException;
import ru.vsu.csf.asashina.musicmanBack.mapper.PlaylistMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Playlist;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.repository.PlaylistRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;

    private final PageUtil pageUtil;

    private final SongService songService;
    private final RecommendationService recommendationService;

    public Page<PlaylistDTO> getAll(Long userId, Integer pageNumber, Integer size, String name) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<Playlist> playlists = playlistRepository.getAll(userId, name, pageRequest);
        pageUtil.checkPageOutOfRange(playlists, pageNumber);
        return playlists.map(playlistMapper::toDTOFromEntity);
    }

    @Transactional
    public PlaylistWithSongsDTO getPlaylistById(UUID id, boolean isAdmin, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, isAdmin);
        return playlistMapper.toDTOWithSongFromEntity(playlist);
    }

    private void checkUsersAccessToPlaylist(Playlist playlist, Long userId, boolean isAdmin) {
        if (!playlist.getUser().getUserId().equals(userId) && !isAdmin) {
            throw new AccessDeniedException("Нет доступа к плейлисту");
        }
    }

    private Playlist findPlaylistById(UUID id) {
        return playlistRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Плейлиста с данным ИД не существует"));
    }

    @Transactional
    public void createPlaylist(CreatePlaylistRequest request, UserDTO user) {
        Playlist playlist = playlistMapper.toEntityFromRequest(request, user);
        playlistRepository.saveAndFlush(playlist);
    }

    @Transactional
    public SongExistsDTO isSongInPlaylist(UUID id, Long songId, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, false);
        songService.getSongById(songId);
        return new SongExistsDTO(playlistRepository.isSongInPlaylist(playlist.getPlaylistId(), songId));
    }

    @Transactional
    public void addSongToPlaylist(UUID id, Long songId, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, false);
        songService.getSongById(songId);
        if (playlistRepository.isSongInPlaylist(playlist.getPlaylistId(), songId)) {
            throw new EntityAlreadyExistsException("Песня уже есть в плейлисте");
        }
        recommendationService.deleteFromRecommendation(userId, songId);
        playlistRepository.addSongToPlaylist(id, songId);
    }

    @Transactional
    public void updatePlaylist(UUID id, UpdatePlaylistRequest request, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, false);
        playlistMapper.updateEntity(request, playlist);
        playlistRepository.save(playlist);
    }

    @Transactional
    public void deletePlaylist(UUID id, Long userId, boolean isAdmin) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, isAdmin);
        playlistRepository.delete(playlist);
    }

    @Transactional
    public void deleteSongFromPlaylist(UUID id, Long songId, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, false);
        songService.getSongById(songId);
        if (!playlistRepository.isSongInPlaylist(playlist.getPlaylistId(), songId)) {
            throw new NoSongInCollectionException("Песни нет в плейлисте");
        }
        playlistRepository.deleteSongFromPlaylist(id, songId);
    }
}
