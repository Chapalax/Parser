package ru.tinkoff.edu.java.scrapper.domain.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class LinkDataRowMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        Link link = new Link();
        link.setId(rs.getLong("id"));
        link.setDomain(rs.getString("domain"));
        link.setPath(rs.getString("path"));
        link.setLastActivity(rs.getObject("last_activity", OffsetDateTime.class));
        return link;
    }
}
