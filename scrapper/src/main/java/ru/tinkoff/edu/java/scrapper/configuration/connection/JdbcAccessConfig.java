package ru.tinkoff.edu.java.scrapper.configuration.connection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repositories.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repositories.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repositories.JdbcTrackRepository;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {

    @Bean
    public LinkRepository linkRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcLinkRepository(jdbcTemplate);
    }

    @Bean
    public TgChatRepository tgChatRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcTgChatRepository(jdbcTemplate);
    }

    @Bean
    public TrackRepository trackRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcTrackRepository(jdbcTemplate);
    }
}
