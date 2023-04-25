package ru.tinkoff.edu.java.scrapper.configuration.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.generated.JpaLinkEntityRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.generated.JpaTgChatEntityRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.generated.JpaTrackEntityRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repositories.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repositories.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repositories.JpaTrackRepository;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    @Bean
    public LinkRepository linkRepository(JpaLinkEntityRepository linkEntityRepository) {
        log.info("Creating JPA beans...");
        return new JpaLinkRepository(linkEntityRepository);
    }

    @Bean
    public TgChatRepository tgChatRepository(JpaTgChatEntityRepository tgChatEntityRepository) {
        return new JpaTgChatRepository(tgChatEntityRepository);
    }

    @Bean
    public TrackRepository trackRepository(JpaTrackEntityRepository trackEntityRepository) {
        return new JpaTrackRepository(trackEntityRepository);
    }
}
