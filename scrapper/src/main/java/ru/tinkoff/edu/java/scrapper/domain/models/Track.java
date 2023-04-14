package ru.tinkoff.edu.java.scrapper.domain.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Track {
    private Long chatId;
    private Long linkId;
}
