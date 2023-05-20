package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.History;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {

    @Query("""
            SELECT h
            FROM History h
            JOIN h.user u
                ON u.userId = :userId
            ORDER BY h.playTimestamp""")
    List<History> findByUserId(@Param("userId") Long userId);
}
