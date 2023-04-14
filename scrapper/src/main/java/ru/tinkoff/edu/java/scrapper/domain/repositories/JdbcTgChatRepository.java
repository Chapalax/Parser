package ru.tinkoff.edu.java.scrapper.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.Dao;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository implements Dao<TgChat> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<TgChat> rowMapper = new DataClassRowMapper<>(TgChat.class);

    private final String SQL_ADD = "INSERT INTO chats (id) VALUES (:id)";
    private final String SQL_REMOVE = "DELETE FROM chats WHERE id=:id";
    private final String SQL_FIND_ALL = "SELECT * FROM chats";

    @Override
    public int add(TgChat object) {
        return jdbcTemplate.update(SQL_ADD, Map.of("id", object.getId()));
    }

    @Override
    public int remove(TgChat object) {
        return jdbcTemplate.update(SQL_REMOVE, Map.of("id", object.getId()));
    }

    @Override
    public List<TgChat> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }
}
