package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {

    @Query("""
            SELECT p
            FROM Playlist p
            JOIN p.user u
                ON u.userId = :userId
            WHERE LOWER(s.name) LIKE CONCAT('%', LOWER(:name), '%')""")
    Page<Playlist> getAll(@Param("userId") Long userId, @Param("name") String name, Pageable pageable);

    @Query(value = """
            SELECT EXISTS(SELECT 1 FROM playlist_song WHERE song_id = :songId AND playlist_id = :playlistId)""",
            nativeQuery = true)
    boolean isSongInPlaylist(@Param("playlistId") String playlistId, @Param("songId") Long songId);

    @Modifying
    @Query(value = """
            INSERT INTO playlist_song(playlist_song_id, playlist_id, song_id)
            VALUES(:playlistSongId, :playlistId, :songId)""", nativeQuery = true)
    void addSongToPlaylist(@Param("playlistSongId") String playlistSongId,
                               @Param("playlistId") String playlistId,
                               @Param("songId") Long songId);
}
