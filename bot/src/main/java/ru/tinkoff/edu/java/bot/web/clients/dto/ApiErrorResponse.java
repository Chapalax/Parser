package ru.tinkoff.edu.java.bot.web.clients.dto;

import lombok.*;


/**
 * Class representing error in communication between user and bot <p>
 * Internal properties:
 * 
 * <ol>
 * <li>{@link ApiErrorResponse#description} of what went wrong (Nerwork level) </li>
 * <li> HTTP {@link ApiErrorResponse#code} sent as a response to user </li>
 * <li> {@link ApiErrorResponse#exceptionName} Name of generated Java's exception </li>
 * <li> {@link ApiErrorResponse#exceptionMessage} Details of what went wrong in Java's exception </li>
 * <li> {@link ApiErrorResponse#stacktrace} Java's Stack Trace generated when processing bad request </li>
 * </ol> 
 * 
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiErrorResponse extends RuntimeException {
    /** 
     * Description of what went wrong (Network level)
    */
    String description;

    /**
     * Code sent as a response to user
     */
    String code;

    /**
     * Name of generated Java's exception
     */
    String exceptionName;

    /**
     * Details of what went wrong in Java's exception
     */
    String exceptionMessage;

    /**
     * Java's Stack Trace generated when processing bad request
     */
    String[] stacktrace;

}
