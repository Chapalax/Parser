package ru.tinkoff.edu.java.scrapper.dto;

import java.util.ArrayList;

public record ListLinksResponse(ArrayList<LinkResponse> links, int size) {
}
