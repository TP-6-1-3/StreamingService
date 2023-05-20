package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.exception.NoSongInCollectionException;
import ru.vsu.csf.asashina.musicmanBack.mapper.PlaylistMapper;
import ru.vsu.csf.asashina.musicmanBack.mapper.SongMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.*;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Playlist;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.repository.PlaylistRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;
import ru.vsu.csf.asashina.musicmanBack.utils.UuidUtil;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;
    private final SongMapper songMapper;

    private final PageUtil pageUtil;

    private final SongService songService;

    public Page<PlaylistDTO> getAll(Long userId, Integer pageNumber, Integer size, String name) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<Playlist> playlists = playlistRepository.getAll(userId, name, pageRequest);
        pageUtil.checkPageOutOfRange(playlists, pageNumber);
        return playlists.map(playlistMapper::toDTOFromEntity);
    }

    public PlaylistWithSongsDTO getPlaylistById(String id, boolean isAdmin, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, isAdmin);
        return playlistMapper.toDTOWithSongFromEntity(playlist);
    }

    private void checkUsersAccessToPlaylist(Playlist playlist, Long userId, boolean isAdmin) {
        if (!playlist.getUser().getUserId().equals(userId) || !isAdmin) {
            throw new AccessDeniedException("Нет доступа к плейлисту");
        }
    }

    private Playlist findPlaylistById(String id) {
        return playlistRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Плейлиста с данным ИД не существует"));
    }

    @Transactional
    public void createPlaylist(CreatePlaylistRequest request, UserDTO user) {
        Playlist playlist = playlistMapper.toEntityFromRequest(
                UuidUtil.generateRandomUUIDInString(), request, user);
        playlistRepository.save(playlist);
    }

    public SongExistsDTO isSongInPlaylist(String id, Long songId, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, false);
        songService.getSongById(songId);
        return new SongExistsDTO(isSongInPlaylist(playlist, songId));
    }

    private boolean isSongInPlaylist(Playlist playlist, Long songId) {
        return playlist.getSongs().stream().anyMatch(song -> song.getSongId().equals(songId));
    }

    @Transactional
    public void addSongToPlaylist(String id, Long songId, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, false);
        SongDTO song = songService.getSongById(songId);
        if (isSongInPlaylist(playlist, songId)) {
            throw new EntityAlreadyExistsException("Песня уже есть в плейлисте");
        }
        playlist.addSong(songMapper.toEntityFromDTO(song));
        playlistRepository.save(playlist);
    }

    @Transactional
    public void updatePlaylist(String id, UpdatePlaylistRequest request, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, false);
        playlistMapper.updateEntity(request, playlist);
        playlistRepository.save(playlist);
    }

    @Transactional
    public void deletePlaylist(String id, Long userId, boolean isAdmin) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, isAdmin);
        playlistRepository.delete(playlist);
    }

    @Transactional
    public void deleteSongFromPlaylist(String id, Long songId, Long userId) {
        Playlist playlist = findPlaylistById(id);
        checkUsersAccessToPlaylist(playlist, userId, false);
        SongDTO song = songService.getSongById(songId);
        if (!isSongInPlaylist(playlist, songId)) {
            throw new NoSongInCollectionException("Песни нет в плейлисте");
        }
        playlist.deleteSong(songMapper.toEntityFromDTO(song));
        playlistRepository.save(playlist);
    }
}
