package ru.tinkoff.edu.java.scrapper.web.clients.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiErrorResponse extends Exception{
    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    ArrayList<String> stacktrace;
}
