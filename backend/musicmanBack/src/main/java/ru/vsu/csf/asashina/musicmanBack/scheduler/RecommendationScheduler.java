package ru.vsu.csf.asashina.musicmanBack.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.vsu.csf.asashina.musicmanBack.service.RecommendationService;

@Slf4j
@Component
@AllArgsConstructor
public class RecommendationScheduler {

    private final RecommendationService recommendationService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cancelBooking() {
        recommendationService.deleteOldRecommendations();
        log.info("Удаление старых рекомендованных песен");
    }
}
