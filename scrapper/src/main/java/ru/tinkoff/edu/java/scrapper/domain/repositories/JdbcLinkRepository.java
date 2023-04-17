package ru.tinkoff.edu.java.scrapper.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.mappers.LinkDataRowMapper;
import ru.tinkoff.edu.java.scrapper.models.Link;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new LinkDataRowMapper();

    private final String SQL_ADD = "INSERT INTO links (domain, path, last_activity) " +
            "VALUES (:domain, :path, :lastActivity) RETURNING *";
    private final String SQL_REMOVE = "DELETE FROM links WHERE domain=:domain AND path=:path";
    private final String SQL_FIND_ALL = "SELECT * FROM links";
    private final String SQL_IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM links WHERE domain=:domain and path=:path)";
    private final String SQL_FIND_ONE_BY_URL = "SELECT 1 FROM links WHERE domain=:domain AND path=:path";
    private final String SQL_FIND_ONE_BY_ID = "SELECT 1 FROM links WHERE id=:id";

    @Override
    @Transactional
    public Link add(Link object) {
        return jdbcTemplate.queryForObject(SQL_ADD, new BeanPropertySqlParameterSource(object), rowMapper);
    }

    @Override
    @Transactional
    public int remove(Link object) {
        return jdbcTemplate.update(SQL_REMOVE,
                Map.of("domain", object.getDomain(),
                        "path", object.getPath()));
    }

    @Override
    @Transactional
    public List<Link> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    @Transactional
    public Boolean isExists(Link link) {
        return jdbcTemplate.queryForObject(SQL_IS_EXISTS,
                Map.of("domain", link.getDomain(),
                "path", link.getPath()),
                Boolean.class);
    }

    @Override
    @Transactional
    public Link findByUrl(Link link) {
        return jdbcTemplate.queryForObject(SQL_FIND_ONE_BY_URL, new BeanPropertySqlParameterSource(link), rowMapper);
    }

    @Override
    @Transactional
    public Link findById(Link link) {
        return jdbcTemplate.queryForObject(SQL_FIND_ONE_BY_ID, new BeanPropertySqlParameterSource(link), rowMapper);
    }
}
