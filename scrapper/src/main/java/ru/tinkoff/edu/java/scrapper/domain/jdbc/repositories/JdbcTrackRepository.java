package ru.tinkoff.edu.java.scrapper.domain.jdbc.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.mappers.TrackDataRowMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;
import ru.tinkoff.edu.java.scrapper.domain.models.Track;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcTrackRepository implements TrackRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Track> rowMapper = new TrackDataRowMapper();

    private final String SQL_ADD = "INSERT INTO tracking (chat_id, link_id) VALUES (:chatId, :linkId) RETURNING *";
    private final String SQL_REMOVE = "DELETE FROM tracking WHERE chat_id=:chatId AND link_id=:linkId";
    private final String SQL_FIND_ALL = "SELECT * FROM tracking";
    private final String SQL_IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM tracking WHERE chat_id=:chatId AND link_id=:linkId)";
    private final String SQL_IS_TRACKED_BY = "SELECT EXISTS(SELECT * FROM tracking WHERE link_id=:linkId)";

    @Override
    @Transactional
    public Track add(@NotNull Track object) {
        return jdbcTemplate.queryForObject(SQL_ADD, new BeanPropertySqlParameterSource(object), rowMapper);
    }

    @Override
    @Transactional
    public int remove(@NotNull Track object) {
        return jdbcTemplate.update(SQL_REMOVE,
                Map.of("chatId", object.getChatId(),
                        "linkId", object.getLinkId()));
    }

    @Override
    @Transactional
    public List<Track> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    @Transactional
    public Boolean isTracked(@NotNull TgChat chat, @NotNull Link link) {
        return jdbcTemplate.queryForObject(SQL_IS_EXISTS,
                Map.of("chatId", chat.getId(),
                        "linkId", link.getId()),
                Boolean.class);
    }

    @Override
    @Transactional
    public Boolean isTrackedByAnyone(@NotNull Link link) {
        return jdbcTemplate.queryForObject(SQL_IS_TRACKED_BY, Map.of("linkId", link.getId()), Boolean.class);
    }

    @Override
    @Transactional
    public List<Track> findAllTracksByUser(@NotNull TgChat chat) {
        return findAll()
                .stream()
                .filter(track -> track.getChatId().equals(chat.getId()))
                .toList();
    }

    @Override
    @Transactional
    public List<Track> findAllTracksWithLink(@NotNull Link link) {
        return findAll()
                .stream()
                .filter(track -> track.getLinkId().equals(link.getId()))
                .toList();
    }
}
