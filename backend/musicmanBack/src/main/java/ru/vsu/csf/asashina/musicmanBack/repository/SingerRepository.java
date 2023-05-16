package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Singer;

@Repository
public interface SingerRepository extends JpaRepository<Singer, Long> {

    @Query(value = """
            SELECT s
            FROM Singer s
            WHERE LOWER(s.fullName) LIKE CONCAT('%', LOWER(:name), '%')
            """)
    Page<Singer> getAll(@Param("name") String name, Pageable pageable);
}
