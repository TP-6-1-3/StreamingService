package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.RefreshToken;

import java.time.Instant;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    @Modifying
    @Query(value = """
                    INSERT INTO refresh_token(refresh_token, expire_date, user_id)
                    VALUES(:refreshToken, :expireDate, :userId)""", nativeQuery = true)
    void saveNewRefreshToken(@Param("refreshToken") String refreshToken,
                             @Param("expireDate") Instant expireDate,
                             @Param("userId") Long userId);
}
