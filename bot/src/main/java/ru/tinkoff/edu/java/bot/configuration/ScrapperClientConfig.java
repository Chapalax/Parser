package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.bot.web.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;


/**
 * Builder class designed for binding existing scrapper to new client
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Validated
@ConfigurationProperties(prefix = "baseurl", ignoreUnknownFields = false)
public record ScrapperClientConfig(@NotNull String scrapper) {

    /**
     * Builder method for binding existing scrapper to new client
     * @return Modified instance of entity of common interface for scrapers
     * @see WebClientScrapper
     */
    @Bean
    public @NotNull WebClientScrapper scrapperClient() {
        return new ScrapperClient(scrapper);
    }
}
