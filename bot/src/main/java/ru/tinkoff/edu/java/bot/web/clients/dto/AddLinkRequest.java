package ru.tinkoff.edu.java.bot.web.clients.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

/**
 * DTO representing request to add new repository for concrete user
 * @param link URL to concrete repository
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public record AddLinkRequest(@NotNull @URL String link) {
}
