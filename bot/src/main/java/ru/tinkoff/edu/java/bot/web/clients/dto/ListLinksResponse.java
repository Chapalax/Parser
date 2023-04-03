package ru.tinkoff.edu.java.bot.web.clients.dto;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record ListLinksResponse(@NotNull ArrayList<LinkResponse> links, int size) {
}
