package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Recommendation;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, UUID> {

    @Query("""
            SELECT r
            FROM Recommendation r
            JOIN r.user u
                ON u.userId = :userId
            ORDER BY r.till DESC""")
    List<Recommendation> findAllByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT r
            FROM Recommendation r
            JOIN r.user u
                ON u.userId = :userId
            JOIN r.song s
                s.songId = :songId""")
    Optional<Recommendation> findByUserIdAndSongId(@Param("userId") Long userId, @Param("songId") Long songId);

    @Modifying
    @Query("""
            DELETE
            FROM Recommendation r
            JOIN r.user u
                ON u.userId = :userId
            JOIN r.song s
                s.songId = :songId""")
    void deleteByUserIdAndSongId(@Param("userId") Long userId, @Param("songId") Long songId);

    @Modifying
    @Query("""
            DELETE
            FROM Recommendation r
            JOIN r.user u
                ON u.userId = :userId
            JOIN r.song s
                s.songId = :songId""")
    void deleteOld(@Param("now") Instant now);
}
