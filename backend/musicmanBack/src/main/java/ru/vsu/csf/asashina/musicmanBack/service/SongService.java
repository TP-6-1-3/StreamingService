package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.exception.SongFileException;
import ru.vsu.csf.asashina.musicmanBack.mapper.SongMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SingerDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongPageDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Genre;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Song;
import ru.vsu.csf.asashina.musicmanBack.model.enumeration.SongSort;
import ru.vsu.csf.asashina.musicmanBack.model.request.AddGenresToSongRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreateSongRequest;
import ru.vsu.csf.asashina.musicmanBack.repository.SongRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    private final PageUtil pageUtil;

    private final SingerService singerService;
    private final GenreService genreService;

    @Value("${songs.directory}")
    private String songsDirectoryPath;

    public Page<SongPageDTO> getAllSongs(
            Integer pageNumber,
            Integer size,
            String title,
            SongSort sort,
            Boolean isAsc,
            List<Long> genreIds,
            Long singerId) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size, isAsc, sort.getQueryParam());
        Page<Song> songs;
        if (genreIds.isEmpty()) {
            songs = songRepository.getAll(singerId, title, pageRequest);
        } else {
            songs = songRepository.getAll(singerId, genreIds, title, pageRequest);
        }
        pageUtil.checkPageOutOfRange(songs, pageNumber);
        return songs.map(songMapper::toPageDTOFromEntity);
    }

    public Page<SongPageDTO> getUsersSongs(Long userId, Integer pageNumber, Integer size, String title) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<Song> songs = songRepository.getUsersAll(userId, title, pageRequest);
        pageUtil.checkPageOutOfRange(songs, pageNumber);
        return songs.map(songMapper::toPageDTOFromEntity);
    }

    public SongDTO getSongById(Long id) {
        return songMapper.toDTOFromEntity(findSongById(id));
    }

    public File getFileFromSystem(Long id) {
        findSongById(id);
        return new File(songsDirectoryPath.concat("/").concat(Long.toString(id)));
    }

    @Transactional
    public void createSong(CreateSongRequest request) throws UnsupportedAudioFileException, IOException {
        SingerDTO singer = singerService.getSingerById(request.getSingerId());
        Set<GenreDTO> genres = genreService.getGenresByIds(request.getGenreIds());
        Song song = songMapper.toEntityFromRequest(request, singer, genres);
        validateSongFile(request.getFile());
        song = songRepository.save(song);

        String filePath = songsDirectoryPath.concat("/").concat(Long.toString(song.getSongId()));
        File directory = new File(filePath);
        request.getFile().transferTo(directory);
    }

    private void validateSongFile(MultipartFile file) throws IOException, UnsupportedAudioFileException {
        if (file == null || file.isEmpty()) {
            throw new SongFileException("Вы не можете загрузить пустой файл");
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).contains(".mp3")) {
            throw new SongFileException("Тип файла должен быть .mp3");
        }

        ByteArrayInputStream in = new ByteArrayInputStream(file.getBytes());
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(in);
        in.close();
        audioStream.close();
    }

    @Transactional
    public void addGenresToSong(Long id, AddGenresToSongRequest request) {
        Song song = findSongById(id);
        Set<GenreDTO> genres = genreService.getGenresByIds(request.getGenresIds());
        List<Long> genresIdsInSong = song.getGenres().stream()
                .map(Genre::getGenreId)
                .toList();
        List<Long> genresIdsFiltered = genres.stream()
                .map(GenreDTO::getGenreId)
                .filter(genreId -> !genresIdsInSong.contains(genreId))
                .toList();
        songRepository.addGenresToSong(song.getSongId(), genresIdsFiltered);
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

    @Transactional
    public void addSongToUsersLibrary(Long userId, Long songId) {
        Song song = findSongById(songId);
        if (songRepository.isSongAlreadyInUsersLibrary(songId, userId)) {
            throw new EntityAlreadyExistsException("Песня уже есть в аудиотеке");
        }
        songRepository.addSongToUsersLibrary(songId, userId);
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
