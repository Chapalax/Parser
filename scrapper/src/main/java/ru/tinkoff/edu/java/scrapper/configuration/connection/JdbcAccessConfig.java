package ru.tinkoff.edu.java.scrapper.configuration.connection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfig {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Bean
    public LinkRepository linkRepository() {
        log.info("Creating JDBC beans...");
        return new JdbcLinkRepository(jdbcTemplate);
    }

    @Bean
    public TgChatRepository tgChatRepository() {
        return new JdbcTgChatRepository(jdbcTemplate);
    }

    @Bean
    public TrackRepository trackRepository() {
        return new JdbcTrackRepository(jdbcTemplate);
    }
}
