package ru.tinkoff.edu.java.scrapper.domain.jpa.mappers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.TrackEntity;
import ru.tinkoff.edu.java.scrapper.domain.models.Track;

@Component
public class TrackEntityMapper {
    public Track map(@NotNull TrackEntity entity) {
        Track track = new Track();
        track.setChatId(entity.getChatId());
        track.setLinkId(entity.getLinkId());
        return track;
    }

    public @NotNull TrackEntity unmap(@NotNull Track track) {
        TrackEntity entity = new TrackEntity();
        entity.setChatId(track.getChatId());
        entity.setLinkId(track.getLinkId());
        return entity;
    }
}
