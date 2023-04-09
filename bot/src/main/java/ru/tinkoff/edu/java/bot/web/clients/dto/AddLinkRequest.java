package ru.tinkoff.edu.java.bot.web.clients.dto;

import jakarta.validation.constraints.NotNull;

public record AddLinkRequest(@NotNull String link) {
}
