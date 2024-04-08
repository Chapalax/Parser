package ru.tinkoff.edu.java.bot.dto;

import java.util.ArrayList;

/**
 * DTO representing an error generated in the process of processing user request.
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public record ApiErrorResponse(String description, String code, String exceptionName,
                               String exceptionMessage, ArrayList<String> stacktrace) {
}
