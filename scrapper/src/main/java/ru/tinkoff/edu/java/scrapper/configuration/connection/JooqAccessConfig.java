package ru.tinkoff.edu.java.scrapper.configuration.connection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.mappers.LinkRecordMapper;
import ru.tinkoff.edu.java.scrapper.domain.jooq.mappers.LinkRecordUnmapper;
import ru.tinkoff.edu.java.scrapper.domain.jooq.repositories.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.repositories.JooqTgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.repositories.JooqTrackRepository;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfig {

    private final DSLContext dslContext;

    @Bean
    public LinkRecordMapper linkRecordMapper() {
        return new LinkRecordMapper();
    }

    @Bean
    public LinkRecordUnmapper linkRecordUnmapper() {
        return new LinkRecordUnmapper(dslContext);
    }

    @Bean
    public LinkRepository linkRepository() {
        log.info("Creating JOOQ beans...");
        return new JooqLinkRepository(dslContext, linkRecordMapper(), linkRecordUnmapper());
    }

    @Bean
    public TgChatRepository tgChatRepository() {
        return new JooqTgChatRepository(dslContext);
    }

    @Bean
    public TrackRepository trackRepository() {
        return new JooqTrackRepository(dslContext);
    }
}
