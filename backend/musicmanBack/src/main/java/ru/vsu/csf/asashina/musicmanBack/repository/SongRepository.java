package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Song;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query(value = """
            SELECT s
            FROM Song s
            JOIN s.singer sing
                ON (:singerId IS NULL OR sing.singerId = :singerId)
            JOIN s.genres g
                ON g.genreId IN (:genreIds)
            WHERE LOWER(s.title) LIKE CONCAT('%', LOWER(:title), '%')""")
    Page<Song> getAll(@Param("singerId") Long singerId,
                      @Param("genreIds") List<Long> genreIds,
                      @Param("title") String title,
                      Pageable pageable);

    @Query(value = """
            SELECT s
            FROM Song s
            JOIN s.singer sing
                ON (:singerId IS NULL OR sing.singerId = :singerId)
            WHERE LOWER(s.title) LIKE CONCAT('%', LOWER(:title), '%')""")
    Page<Song> getAll(@Param("singerId") Long singerId,
                      @Param("title") String title,
                      Pageable pageable);

    @Modifying
    @Query(value = """
            INSERT INTO song_genre(song_id, genre_id)
            SELECT s.song_id, g.genre_id
            FROM song s
            JOIN genre g
                ON g.genre_id IN (:genreIds)
                AND s.song_id = :songId""", nativeQuery = true)
    void addGenresToSong(@Param("songId") Long songId, @Param("genreIds") List<Long> genreIds);

    @Query("""
            SELECT s
            FROM song s
            JOIN s.user u
                ON u.user_id = :userId
            WHERE LOWER(s.title) LIKE CONCAT('%', LOWER(:title), '%')""")
    Page<Song> getUsersAll(@Param("userId") Long userId, @Param("title") String title, Pageable pageable);
}
