package ru.tinkoff.edu.java.scrapper.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Track {
    private Long chatId;
    private Long linkId;
}
