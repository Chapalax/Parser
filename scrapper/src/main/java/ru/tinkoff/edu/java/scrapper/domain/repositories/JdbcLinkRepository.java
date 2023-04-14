package ru.tinkoff.edu.java.scrapper.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.Dao;
import ru.tinkoff.edu.java.scrapper.domain.mappers.LinkDataRowMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements Dao<Link> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new LinkDataRowMapper();

    private final String SQL_ADD = "INSERT INTO links (domain, path, last_activity) VALUES (:domain, :path, :lastActivity)";
    private final String SQL_REMOVE = "DELETE FROM links WHERE id=:id";
    private final String SQL_FIND_ALL = "SELECT * FROM links";

    @Override
    public int add(Link object) {
        return jdbcTemplate.update(SQL_ADD,
                Map.of("domain", object.getDomain(),
                        "path", object.getPath(),
                        "lastActivity", object.getLastActivity()));
    }

    @Override
    public int remove(Link object) {
        return jdbcTemplate.update(SQL_REMOVE, Map.of("id", object.getId()));
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }
}
