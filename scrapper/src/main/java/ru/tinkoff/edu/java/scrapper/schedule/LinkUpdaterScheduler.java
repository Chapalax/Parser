package ru.tinkoff.edu.java.scrapper.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.interfaces.LinkUpdater;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info("Looking for updates...");
        linkUpdater.update();
        log.info("All links are up to update.");
    }
}
