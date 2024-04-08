package ru.tinkoff.edu.java.bot.dto;

import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;

/**
 * DTO designed for updating requests of users for given tgChatsIds with given description, url, and (what id?)
 * @author Maxim Berezhnoy
 * @version 1.0
*/
public record LinkUpdateRequest(long id, @URL String url, String description, ArrayList<Long> tgChatIds) {
}
