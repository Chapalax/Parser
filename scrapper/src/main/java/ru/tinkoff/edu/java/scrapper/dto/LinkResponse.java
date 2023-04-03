package ru.tinkoff.edu.java.scrapper.dto;

import org.hibernate.validator.constraints.URL;

public record LinkResponse(long id, @URL String url) {
}
