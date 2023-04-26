package ru.tinkoff.edu.java.scrapper.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class Link {
    private Long id;
    private String path;
    private OffsetDateTime lastActivity;
    private Integer actionCount;
    private OffsetDateTime checkedAt;
}
