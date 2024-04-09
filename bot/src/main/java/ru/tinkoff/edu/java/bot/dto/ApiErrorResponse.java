package ru.tinkoff.edu.java.bot.dto;

import java.util.ArrayList;

/**
 * DTO representing an error generated in the process of processing user request.
 * @author Maxim Berezhnoy
 * @version 1.0
 * @param description description of what had happened wrong
 * @param code status code returned
 * @param exceptionName name of java's exception that has been generated
 * @param exceptionMessage description of generated exception
 * @param stacktrace stacktrace of the exception
 */
public record ApiErrorResponse(String description, String code, String exceptionName,
                               String exceptionMessage, ArrayList<String> stacktrace) {
}
