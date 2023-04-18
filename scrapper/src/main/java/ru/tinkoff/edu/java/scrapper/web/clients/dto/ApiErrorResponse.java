package ru.tinkoff.edu.java.scrapper.web.clients.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiErrorResponse extends Exception{
    String description;
    String code;
    String exceptionName;
    String exceptionMessage;
    ArrayList<String> stacktrace;
}
