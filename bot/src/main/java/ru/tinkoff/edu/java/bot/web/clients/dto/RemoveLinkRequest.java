package ru.tinkoff.edu.java.bot.web.clients.dto;

import jakarta.validation.constraints.NotNull;

public record RemoveLinkRequest(@NotNull String link) {
}
