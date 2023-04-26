package ru.tinkoff.edu.java.scrapper.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.mappers.TrackDataRowMapper;
import ru.tinkoff.edu.java.scrapper.models.Link;
import ru.tinkoff.edu.java.scrapper.models.TgChat;
import ru.tinkoff.edu.java.scrapper.models.Track;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcTrackRepository implements TrackRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Track> rowMapper = new TrackDataRowMapper();

    private final String SQL_ADD = "INSERT INTO tracking (chat_id, link_id) VALUES (:chatId, :linkId) RETURNING *";
    private final String SQL_REMOVE = "DELETE FROM tracking WHERE chat_id=:chatId AND link_id=:linkId";
    private final String SQL_FIND_ALL = "SELECT * FROM tracking";
    private final String SQL_FIND_ALL_BY_ID = "SELECT * FROM tracking WHERE chat_id=:chatId";
    private final String SQL_IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM tracking WHERE chat_id=:chatId AND link_id=:linkId)";
    private final String SQL_IS_TRACKED_BY = "SELECT EXISTS(SELECT * FROM tracking WHERE link_id=:linkId)";
    private final String SQL_FIND_ALL_BY_LINK = "SELECT * FROM tracking WHERE link_id=:linkId";

    @Override
    @Transactional
    public Track add(Track object) {
        return jdbcTemplate.queryForObject(SQL_ADD, new BeanPropertySqlParameterSource(object), rowMapper);
    }

    @Override
    @Transactional
    public int remove(Track object) {
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
    public Boolean isTracked(TgChat chat, Link link) {
        return jdbcTemplate.queryForObject(SQL_IS_EXISTS,
                Map.of("chatId", chat.getId(),
                        "linkId", link.getId()),
                Boolean.class);
    }

    @Override
    @Transactional
    public Boolean isTrackedByAnyone(Link link) {
        return jdbcTemplate.queryForObject(SQL_IS_TRACKED_BY, Map.of("linkId", link.getId()), Boolean.class);
    }

    @Override
    @Transactional
    public List<Track> findAllTracksByUser(TgChat chat) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_ID, Map.of("chatId", chat.getId()), rowMapper);
    }

    @Override
    public List<Track> findAllTracksWithLink(Link link) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_LINK, Map.of("linkId", link.getId()), rowMapper);
    }
}
