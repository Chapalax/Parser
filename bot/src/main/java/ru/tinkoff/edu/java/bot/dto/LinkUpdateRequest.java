package ru.tinkoff.edu.java.bot.dto;

import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;

/**
 * DTO designed for updating requests of users for given tgChatsIds with given description, url, and id of database
 * @author Maxim Berezhnoy
 * @version 1.0
 * @param id id of database
 * @param url url of repository
 * @param description text of what has been updated
 * @param tgChatIds what users have to be notified of incoming updates in repository
 */
public record LinkUpdateRequest(long id, @URL String url, String description, ArrayList<Long> tgChatIds) {
}
