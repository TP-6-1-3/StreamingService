package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.mapper.SongMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.repository.RecommendationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final SongMapper songMapper;

    public List<SongDTO> getRecommendedSongs(Long userId) {
        return recommendationRepository.findAllByUserId(userId)
                .stream()
                .map(recommendation -> songMapper.toDTOFromEntity(recommendation.getSong()))
                .toList();
    }
}
