package ru.tinkoff.edu.java.scrapper.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.web.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.web.clients.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientGitHub;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientStackOverflow;

@Validated
@ConfigurationProperties(prefix = "baseurl", ignoreUnknownFields = false)
public record WebClientConfig(String github, String stackoverflow) {

    @Bean
    public @NotNull WebClientGitHub gitHubClient() {
        return new GitHubClient(github);
    }

    @Bean
    public @NotNull WebClientStackOverflow stackOverflowClient() {
        return new StackOverflowClient(stackoverflow);
    }
}
