package ru.tinkoff.edu.java.scrapper.domain.jdbc.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.models.Track;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDataRowMapper implements RowMapper<Track> {
    @Override
    public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
        Track track = new Track();
        track.setChatId(rs.getLong("chat_id"));
        track.setLinkId(rs.getLong("link_id"));
        return track;
    }
}
