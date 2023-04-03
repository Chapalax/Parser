package ru.tinkoff.edu.java.scrapper.web.clients.interfaces;

import ru.tinkoff.edu.java.scrapper.web.clients.dto.StackOverflowResponse;

public interface WebClientStackOverflow {
    StackOverflowResponse fetchStackOverflowQuestion(String id);
}
