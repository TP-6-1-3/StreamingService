package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Set<Genre> findAllByGenreIdIn(List<Long> ids);

    Optional<Genre> findGenreByName(String name);
}
