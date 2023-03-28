package ru.tinkoff.edu.java.scrapper.dto;

import java.util.ArrayList;

public record ApiErrorResponse(String description, String code, String exceptionName,
                               String exceptionMessage, ArrayList<String> stacktrace) {
}
