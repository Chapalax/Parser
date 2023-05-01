package ru.tinkoff.edu.java.scrapper.dto;

import java.net.URI;
import java.util.ArrayList;

public record LinkUpdateResponse(long id, URI url, String description, ArrayList<Long> tgChatIds) {
}
