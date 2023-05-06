package ru.tinkoff.edu.java.scrapper.domain.jooq.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.Tracking;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;
import ru.tinkoff.edu.java.scrapper.domain.models.Track;

import java.util.List;

@RequiredArgsConstructor
public class JooqTrackRepository implements TrackRepository {

    private final DSLContext dsl;

    @Override
    @Transactional
    public Track add(@NotNull Track object) {
        return dsl.insertInto(Tracking.TRACKING)
                .set(dsl.newRecord(Tracking.TRACKING, object))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(
                        "Error inserting entity: " + object.getChatId() + " " + object.getLinkId()))
                .into(Track.class);
    }

    @Override
    @Transactional
    public int remove(@NotNull Track object) {
        return dsl.deleteFrom(Tracking.TRACKING)
                .where(Tracking.TRACKING.CHAT_ID.eq(object.getChatId()))
                .and(Tracking.TRACKING.LINK_ID.eq(object.getLinkId()))
                .execute();
    }

    @Override
    @Transactional
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
    @Transactional
    public Boolean isTracked(@NotNull TgChat chat, @NotNull Link link) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(Tracking.TRACKING)
                        .where(Tracking.TRACKING.CHAT_ID.eq(chat.getId()))
                        .and(Tracking.TRACKING.LINK_ID.eq(link.getId()))
        );
    }

    @Override
    @Transactional
    public Boolean isTrackedByAnyone(@NotNull Link link) {
        return dsl.fetchExists(
                dsl.selectFrom(Tracking.TRACKING)
                        .where(Tracking.TRACKING.LINK_ID.eq(link.getId()))
        );
    }

    @Override
    @Transactional
    public List<Track> findAllTracksByUser(@NotNull TgChat chat) {
        return dsl.selectFrom(Tracking.TRACKING)
                .where(Tracking.TRACKING.CHAT_ID.eq(chat.getId()))
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
    @Transactional
    public List<Track> findAllTracksWithLink(@NotNull Link link) {
        return dsl.selectFrom(Tracking.TRACKING)
                .where(Tracking.TRACKING.LINK_ID.eq(link.getId()))
                .stream()
                .map(record -> {
                    Track track = new Track();
                    track.setChatId(record.getChatId());
                    track.setLinkId(record.getLinkId());
                    return track;
                })
                .toList();
    }
}
