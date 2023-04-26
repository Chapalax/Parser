package ru.tinkoff.edu.java.scrapper.domain.jooq.repositories;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.Tracking;
import ru.tinkoff.edu.java.scrapper.models.Link;
import ru.tinkoff.edu.java.scrapper.models.TgChat;
import ru.tinkoff.edu.java.scrapper.models.Track;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JooqTrackRepository implements TrackRepository {

    private final DSLContext dsl;

    @Override
    public Track add(Track object) {
        return dsl.insertInto(Tracking.TRACKING)
                .set(dsl.newRecord(Tracking.TRACKING, object))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(
                        "Error inserting entity: " + object.getChatId() + " " + object.getLinkId()))
                .into(Track.class);
    }

    @Override
    public int remove(Track object) {
        return dsl.deleteFrom(Tracking.TRACKING)
                .where(Tracking.TRACKING.CHAT_ID.eq(object.getChatId()))
                .and(Tracking.TRACKING.LINK_ID.eq(object.getLinkId()))
                .execute();
    }

    @Override
    public List<Track> findAll() {
        return dsl.selectFrom(Tracking.TRACKING)
                .stream()
                .map(record -> {
                    Track track = new Track();
                    track.setChatId(record.getChatId());
                    track.setLinkId(record.getLinkId());
                    return track;
                })
                .toList();
    }

    @Override
    public Boolean isTracked(TgChat chat, Link link) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(Tracking.TRACKING)
                        .where(Tracking.TRACKING.CHAT_ID.eq(chat.getId()))
                        .and(Tracking.TRACKING.LINK_ID.eq(link.getId()))
        );
    }

    @Override
    public Boolean isTrackedByAnyone(Link link) {
        return dsl.fetchExists(
                dsl.selectFrom(Tracking.TRACKING)
                        .where(Tracking.TRACKING.LINK_ID.eq(link.getId()))
        );
    }

    @Override
    public List<Track> findAllTracksByUser(TgChat chat) {
        return findAll()
                .stream()
                .filter(track -> track.getChatId().equals(chat.getId()))
                .toList();
    }

    @Override
    public List<Track> findAllTracksWithLink(Link link) {
        return findAll()
                .stream()
                .filter(track -> track.getLinkId().equals(link.getId()))
                .toList();
    }
}
