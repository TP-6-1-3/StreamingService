package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Statistic;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, UUID> {

    @Query("""
            SELECT s
            FROM Statistic s
            JOIN s.user u
                ON u.userId = :userId
            JOIN s.genre g
                ON g.genreId IN (:genreIds)""")
    List<Statistic> findByUserIdAndGenreId(@Param("userId") Long userId, @Param("genreIds") List<Long> genreIds);

    @Query("""
            SELECT s
            FROM Statistic s
            JOIN s.user u
                ON u.userId = :userId""")
    List<Statistic> findByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT s
            FROM Statistic s
            JOIN s.genre g
                ON g.genreId = :genreId""")
    List<Statistic> findByGenreId(@Param("genreId") Long genreId);
}
