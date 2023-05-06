package ru.tinkoff.edu.java.scrapper.web.clients.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public record GitHubResponse(@NotNull @JsonSetter("full_name") String fullName,
                             @NotNull @JsonSetter("pushed_at") OffsetDateTime updatedAt,
                             @NotNull @JsonSetter("open_issues_count") Integer issuesCount) {
}
