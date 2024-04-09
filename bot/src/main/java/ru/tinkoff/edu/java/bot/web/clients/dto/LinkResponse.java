package ru.tinkoff.edu.java.bot.web.clients.dto;

import java.net.URI;

/**
 * DTO representing response for concrete user interacting with repository
 * @param id Telegram ID of concrete user
 * @param url URL of concrete repository
 * 
 * @author Maxim Berezhnoy
 * @version 1.0
 */

public record LinkResponse(long id, URI url) {
}
