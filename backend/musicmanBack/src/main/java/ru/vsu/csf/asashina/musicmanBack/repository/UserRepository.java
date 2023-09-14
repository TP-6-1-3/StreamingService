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

    @Query(value = """
            SELECT u.*
            FROM user_info u
            JOIN friend f
                ON u.user_id = f.friend_id
                AND f.user_id = :userId
                AND LOWER(u.nickname) LIKE CONCAT('%', LOWER(:nickname), '%')""", nativeQuery = true)
    Page<User> findAllFriendsByNickname(@Param("userId") Long userId,
                                        @Param("nickname") String nickname,
                                        Pageable pageable);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM friend WHERE user_id = :userId AND friend_id = :friendId)",
            nativeQuery = true)
    boolean isFriend(@Param("userId") Long id, @Param("friendId") Long friendId);

    @Modifying
    @Query(value = """
            INSERT INTO friend(user_id, friend_id)
            VALUES (:userId, :friendId)""", nativeQuery = true)
    void addFriend(@Param("userId") Long id, @Param("friendId") Long friendId);

    @Modifying
    @Query(value = "DELETE FROM friend WHERE user_id = :userId AND friend_id = :friendId", nativeQuery = true)
    void deleteFriend(@Param("userId") Long id, @Param("friendId") Long friendId);
}
