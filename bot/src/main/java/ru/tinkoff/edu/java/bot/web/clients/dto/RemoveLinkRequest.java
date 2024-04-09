package ru.tinkoff.edu.java.bot.web.clients.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

/**
 * DTO representing a request for request to untrack a repository for concrete user
 * @param link
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public record RemoveLinkRequest(@NotNull @URL String link) {
}
