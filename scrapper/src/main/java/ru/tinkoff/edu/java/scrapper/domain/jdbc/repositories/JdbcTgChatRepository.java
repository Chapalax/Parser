package ru.tinkoff.edu.java.scrapper.domain.jdbc.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository implements TgChatRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<TgChat> rowMapper = new DataClassRowMapper<>(TgChat.class);

    private final String SQL_ADD = "INSERT INTO chats (id) VALUES (:id) RETURNING *";
    private final String SQL_REMOVE = "DELETE FROM chats WHERE id=:id";
    private final String SQL_FIND_ALL = "SELECT * FROM chats";
    private final String SQL_IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM chats WHERE id=:id)";

    @Override
    @Transactional
    public TgChat add(@NotNull TgChat object) {
        return jdbcTemplate.queryForObject(SQL_ADD, new BeanPropertySqlParameterSource(object), rowMapper);
    }

    @Override
    @Transactional
    public int remove(@NotNull TgChat object) {
        return jdbcTemplate.update(SQL_REMOVE, Map.of("id", object.getId()));
    }

    @Override
    @Transactional
    public List<TgChat> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    @Transactional
    public Boolean isExists(@NotNull TgChat chat) {
        return jdbcTemplate.queryForObject(SQL_IS_EXISTS, Map.of("id", chat.getId()), Boolean.class);
    }
}
