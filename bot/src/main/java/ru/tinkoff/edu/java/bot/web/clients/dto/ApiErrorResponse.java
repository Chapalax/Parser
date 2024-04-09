package ru.tinkoff.edu.java.bot.web.clients.dto;

import lombok.*;


/**
 * Class representing error in communication between user and bot
 * @param description Description of what went wrong (Nerwork level)
 * @param code HTTP code sent as a response to user
 * @param exceptionName Name of generated Java's exception
 * @param exceptionMessage Details of what went wrong in Java's exception
 * @param stackTrace Java's Stack Trace generated when processing bad request
 * 
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiErrorResponse extends RuntimeException {
    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    String[] stacktrace;
}
