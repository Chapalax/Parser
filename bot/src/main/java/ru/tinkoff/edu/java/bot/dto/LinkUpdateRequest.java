package ru.tinkoff.edu.java.bot.dto;

import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;

public record LinkUpdateRequest(long id, @URL String url, String description, ArrayList<Long> tgChatIds) {
}
