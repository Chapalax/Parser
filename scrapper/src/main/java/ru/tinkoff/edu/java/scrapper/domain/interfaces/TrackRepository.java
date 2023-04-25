package ru.tinkoff.edu.java.scrapper.domain.interfaces;

import ru.tinkoff.edu.java.scrapper.domain.models.Link;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;
import ru.tinkoff.edu.java.scrapper.domain.models.Track;

import java.util.List;

public interface TrackRepository extends Dao<Track> {
    Boolean isTracked(TgChat chat, Link link);

    Boolean isTrackedByAnyone(Link link);

    List<Track> findAllTracksByUser(TgChat chat);

    List<Track> findAllTracksWithLink(Link link);
}
