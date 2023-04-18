package ru.tinkoff.edu.java.scrapper.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.interfaces.LinkUpdater;

import java.time.OffsetDateTime;

@Component
@Slf4j
@EnableScheduling
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    @Autowired
    public LinkUpdaterScheduler(LinkUpdater linkUpdater) {
        this.linkUpdater = linkUpdater;
    }

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info("Looking for updates...");
        linkUpdater.update();
        log.info("All links are up to update.");
    }
}
