package ru.tinkoff.edu.java.scrapper.dto;

import org.hibernate.validator.constraints.URL;

public record AddLinkRequest(@URL String link) {
}
