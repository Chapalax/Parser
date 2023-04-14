package ru.tinkoff.edu.java.scrapper.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.Dao;
import ru.tinkoff.edu.java.scrapper.domain.mappers.TrackDataRowMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.Track;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcTrackRepository implements Dao<Track> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Track> rowMapper = new TrackDataRowMapper();

    private final String SQL_ADD = "INSERT INTO tracking (chat_id, link_id) VALUES (:chatId, :linkId)";
    private final String SQL_REMOVE = "DELETE FROM tracking WHERE chat_id=:chatId AND link_id=:linkId";
    private final String SQL_FIND_ALL = "SELECT * FROM tracking";
    @Override
    public int add(Track object) {
        return jdbcTemplate.update(SQL_ADD,
                Map.of("chatId", object.getChatId(),
                        "linkId", object.getLinkId()));
    }

    @Override
    public int remove(Track object) {
        return jdbcTemplate.update(SQL_REMOVE,
                Map.of("chatId", object.getChatId(),
                        "linkId", object.getLinkId()));
    }

    @Override
    public List<Track> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }
}
