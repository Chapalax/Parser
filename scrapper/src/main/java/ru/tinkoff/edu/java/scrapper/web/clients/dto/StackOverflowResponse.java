package ru.tinkoff.edu.java.scrapper.web.clients.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public record StackOverflowResponse(@NotNull String link,
                                    @NotNull @JsonSetter("last_activity_date")OffsetDateTime lastActivity) {
}
