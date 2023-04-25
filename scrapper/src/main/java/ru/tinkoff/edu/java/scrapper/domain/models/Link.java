package ru.tinkoff.edu.java.scrapper.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class Link {
    private Long id;
    private String path;
    private OffsetDateTime lastActivity = OffsetDateTime.now();
    private Integer actionCount = 0;
    private OffsetDateTime checkedAt = OffsetDateTime.now();
}
