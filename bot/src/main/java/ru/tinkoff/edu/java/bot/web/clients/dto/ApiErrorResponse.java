package ru.tinkoff.edu.java.bot.web.clients.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiErrorResponse extends RuntimeException{
    String description;
    String code;
    String exceptionsName;
    String exceptionMessage;
    String[] stacktrace;
}
