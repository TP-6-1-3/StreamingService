package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.exception.NoSongInCollectionException;
import ru.vsu.csf.asashina.musicmanBack.exception.FileException;
import ru.vsu.csf.asashina.musicmanBack.mapper.GenreMapper;
import ru.vsu.csf.asashina.musicmanBack.mapper.SongMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Song;
import ru.vsu.csf.asashina.musicmanBack.model.enumeration.SongSort;
import ru.vsu.csf.asashina.musicmanBack.model.request.AddGenresToSongRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreateSongRequest;
import ru.vsu.csf.asashina.musicmanBack.repository.SongRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    private final SongMapper songMapper;
    private final GenreMapper genreMapper;

    private final PageUtil pageUtil;

    private final SingerService singerService;
    private final GenreService genreService;
    private final StatisticService statisticService;
    private final HistoryService historyService;

    @Value("${songs.directory}")
    private String songsDirectoryPath;

    @Transactional
    public Page<SongDTO> getAllSongs(
            Integer pageNumber,
            Integer size,
            String title,
            SongSort sort,
            Boolean isAsc,
            List<Long> genreIds,
            Long singerId) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size, isAsc, sort.getQueryParam());
        Page<Song> songs;
        if (CollectionUtils.isEmpty(genreIds)) {
            songs = songRepository.getAll(singerId, title, pageRequest);
        } else {
            songs = songRepository.getAll(singerId, genreIds, title, pageRequest);
        }
        pageUtil.checkPageOutOfRange(songs, pageNumber);
        return songs.map(songMapper::toDTOFromEntity);
    }

    @Transactional
    public Page<SongDTO> getUsersSongs(Long userId, Integer pageNumber, Integer size, String title) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<Song> songs = songRepository.getUsersAll(userId, title, pageRequest);
        pageUtil.checkPageOutOfRange(songs, pageNumber);
        return songs.map(songMapper::toDTOFromEntity);
    }

    @Transactional
    public SongDTO getSongById(Long id) {
        return songMapper.toDTOFromEntity(findSongById(id));
    }

    @Transactional
    public File getFileFromSystem(UserDTO user, Long id) {
        SongDTO song = getSongById(id);
        File songFile = new File(songsDirectoryPath.concat("/").concat(Long.toString(id)));
        statisticService.updateStatistic(user, song.getGenres());
        historyService.addSongToUsersHistory(user, song);
        return songFile;
    }

    @Transactional
    public void createSong(CreateSongRequest request) throws IOException {
        SingerDTO singer = singerService.getSingerById(request.getSingerId());
        Set<GenreDTO> genres = genreService.getGenresByIds(request.getGenreIds());
        LocalTime duration = LocalTime.of(0,
                Integer.parseInt(request.getDuration().substring(0, 2)),
                Integer.parseInt(request.getDuration().substring(3)));
        Song song = songMapper.toEntityFromRequest(request, duration, singer, genres);
        validateSongFile(request.getFile());
        song = songRepository.save(song);

        String filePath = songsDirectoryPath.concat("/").concat(Long.toString(song.getSongId()));
        File directory = new File(filePath);
        request.getFile().transferTo(directory);
    }

    private void validateSongFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileException("Вы не можете загрузить пустой файл");
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).contains(".mp3")) {
            throw new FileException("Тип файла должен быть .mp3");
        }
    }

    @Transactional
    public void addGenresToSong(Long id, AddGenresToSongRequest request) {
        Song song = findSongById(id);
        Set<GenreDTO> genres = genreService.getGenresByIds(request.getGenresIds());
        genres.forEach(genre -> song.addGenre(genreMapper.toEntityFromDTO(genre)));
        songRepository.save(song);
    }

    @Transactional
    public void deleteSongById(Long id) {
        Song song = findSongById(id);
        songRepository.delete(song);
        deleteSongFile(id);
    }

    private void deleteSongFile(Long id) {
        File file = new File(songsDirectoryPath.concat("/").concat(id.toString()));
        file.delete();
    }

    private Song findSongById(Long id) {
        return songRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Песня с заданным ИД не существует"));
    }

    public boolean isSongInUsersLibrary(Long id, Long userId) {
        return songRepository.isSongAlreadyInUsersLibrary(id, userId);
    }

    @Transactional
    public void addSongToUsersLibrary(Long id, Long userId) {
        findSongById(id);
        if (isSongInUsersLibrary(userId, id)) {
            throw new EntityAlreadyExistsException("Песня уже есть в аудиотеке");
        }
        songRepository.addSongToUsersLibrary(id, userId);
    }

    @Transactional
    public void deleteSongFromUsersLibrary(Long id, Long userId) {
        findSongById(id);
        if (!isSongInUsersLibrary(userId, id)) {
            throw new NoSongInCollectionException("Песни нет в аудиотеке");
        }
        songRepository.deleteSongFromUsersLibrary(id, userId);
    }

    @PostConstruct
    private void checkIsPathValid() {
        try {
            Paths.get(songsDirectoryPath);
        } catch (InvalidPathException e) {
            log.error("Произошла ошибка при чтении директории с песнями: {}", e.getMessage(), e);
        }
    }
}
