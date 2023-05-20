package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.isVerified = true WHERE u.userId = :id")
    void verifyUserById(@Param("id") Long id);

    @Query("""
            SELECT u
            FROM User u
            JOIN u.friends f
                ON f.userId = :userId
                AND LOWER(f.nickname) LIKE CONCAT('%', LOWER(:nickname), '%')
            """)
    Page<User> findAllFriendsByNickname(@Param("userId") Long userId,
                                        @Param("nickname") String nickname,
                                        Pageable pageable);
}
