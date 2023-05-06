package ru.tinkoff.edu.java.scrapper.domain.jdbc.mappers;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class LinkDataRowMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
        Link link = new Link();
        link.setId(rs.getLong("id"));
        link.setPath(rs.getString("path"));
        link.setLastActivity(rs.getObject("last_activity", OffsetDateTime.class));
        link.setActionCount(rs.getInt("action_count"));
        link.setCheckedAt(rs.getObject("checked_at", OffsetDateTime.class));
        return link;
    }
}
