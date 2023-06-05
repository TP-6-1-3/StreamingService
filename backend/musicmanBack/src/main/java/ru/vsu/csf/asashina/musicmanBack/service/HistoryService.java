package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.vsu.csf.asashina.musicmanBack.mapper.HistoryMapper;
import ru.vsu.csf.asashina.musicmanBack.mapper.SongMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.HistoryDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.History;
import ru.vsu.csf.asashina.musicmanBack.repository.HistoryRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    private final HistoryMapper historyMapper;
    private final SongMapper songMapper;

    @Value("${songs.historySize}")
    private Integer historySize;

    @Async
    @Transactional
    public void addSongToUsersHistory(UserDTO user, SongDTO song) {
        List<History> histories = historyRepository.findByUserId(user.getUserId());
        List<History> updatedHistory = new LinkedList<>();
        if (histories.isEmpty()) {
            updatedHistory.add(historyMapper.createEntity(user, song, Instant.now()));
        } else {
            boolean exists = false;
            for (History history : histories) {
                if (history.getSong().getSongId().equals(song.getSongId())) {
                    history.setPlayTimestamp(Instant.now());
                    exists = true;
                }
                updatedHistory.add(history);
            }
            if (updatedHistory.size() < historySize) {
                updatedHistory.add(historyMapper.createEntity(user, song, Instant.now()));
            } else if (!exists) {
                updatedHistory.set(0, historyMapper.createEntity(user, song, Instant.now()));
            }
        }
        historyRepository.saveAll(updatedHistory);
    }

    @Transactional
    public HistoryDTO getHistory(Long userId) {
        List<History> histories = historyRepository.findByUserId(userId);
        if (CollectionUtils.isEmpty(histories)) {
            return new HistoryDTO(Collections.emptyList());
        }
        return new HistoryDTO(histories.stream()
                .map(history -> songMapper.toDTOFromEntity(history.getSong()))
                .toList());
    }
}
