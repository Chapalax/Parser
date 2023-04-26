package ru.tinkoff.edu.java.scrapper.domain.jdbc.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.mappers.LinkDataRowMapper;
import ru.tinkoff.edu.java.scrapper.models.Link;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {

    @Value("${check.interval}")
    private Long checkInterval;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new LinkDataRowMapper();

    private final String SQL_ADD = "INSERT INTO links (path, last_activity, action_count) " +
            "VALUES (:path, :lastActivity, :actionCount) RETURNING *";
    private final String SQL_REMOVE = "DELETE FROM links WHERE path=:path";
    private final String SQL_FIND_ALL = "SELECT * FROM links";
    private final String SQL_IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM links WHERE path=:path)";
    private final String SQL_FIND_ONE_BY_URL = "SELECT 1 FROM links WHERE path=:path";
    private final String SQL_FIND_ONE_BY_ID = "SELECT 1 FROM links WHERE id=:id";
    private final String SQL_UPDATE_LINK = "UPDATE links SET last_activity=:lastActivity, " +
            "action_count=:actionCount, checked_at=:checkedAt WHERE id=:id";

    @Override
    @Transactional
    public Link add(Link object) {
        if (object.getActionCount() == null) object.setActionCount(0);
        return jdbcTemplate.queryForObject(SQL_ADD, new BeanPropertySqlParameterSource(object), rowMapper);
    }

    @Override
    @Transactional
    public int remove(Link object) {
        return jdbcTemplate.update(SQL_REMOVE, Map.of("path", object.getPath()));
    }

    @Override
    @Transactional
    public List<Link> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    @Transactional
    public Boolean isExists(Link link) {
        return jdbcTemplate.queryForObject(SQL_IS_EXISTS, Map.of("path", link.getPath()), Boolean.class);
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

    @Override
    @Transactional
    public List<Link> findAllToUpdate() {
        return findAll()
                .stream()
                .filter(link -> link
                        .getCheckedAt()
                        .isBefore(OffsetDateTime.now().minusMinutes(checkInterval)))
                .toList();
    }

    @Override
    public void update(Link link) {
        jdbcTemplate.update(SQL_UPDATE_LINK,
                Map.of("lastActivity", link.getLastActivity(),
                        "actionCount", link.getActionCount(),
                        "checkedAt", OffsetDateTime.now(),
                        "id", link.getId()));
    }
}
