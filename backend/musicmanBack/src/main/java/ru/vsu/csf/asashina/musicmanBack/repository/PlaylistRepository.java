package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
