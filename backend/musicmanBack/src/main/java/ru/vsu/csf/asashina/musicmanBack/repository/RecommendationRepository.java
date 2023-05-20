package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Recommendation;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, UUID> {

    @Query("""
            SELECT r
            FROM Recommendation r
            JOIN r.user u
                ON u.userId = :userId""")
    List<Recommendation> findAllByUserId(@Param("userId") Long userId);
}
