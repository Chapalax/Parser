package ru.tinkoff.edu.java.scrapper.service.interfaces;

import ru.tinkoff.edu.java.scrapper.web.clients.dto.LinkUpdateResponse;

public interface MessageSender {
    void send(LinkUpdateResponse update);
}
