package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * DTO intended to use bot token. This DTO is inserted via annotations into others.
 * @author Maxim Berezhnoy
 * @version 1.0
 * @param token Bot token
 * @param queueName Name of chosen queue
 * @param exchangeName Type of exchanging of defined RabbitMQ queue (e.g. Headers, Consistent-Hashing, Direct)
 */
@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String token, @NotNull String queueName, @NotNull String exchangeName) {
}
