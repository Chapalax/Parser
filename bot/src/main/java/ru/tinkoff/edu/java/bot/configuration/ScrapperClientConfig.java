package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.bot.web.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;

@Validated
@ConfigurationProperties(prefix = "baseurl", ignoreInvalidFields = false)
public record ScrapperClientConfig(@NotNull String scrapper) {

    @Bean
    public @NotNull WebClientScrapper scrapperClient() {
        return new ScrapperClient(scrapper);
    }
}
