package ru.tinkoff.edu.java.bot.web.clients.dto;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;


/**
 * DTO representing response for concrete user interacting with multiple repositories and its size
 * @param links List of URL links to repositories with user was interacting
 * @see LinkResponse
 * @param size Size of this very list
 * 
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public record ListLinksResponse(@NotNull ArrayList<LinkResponse> links, int size) {
}
