package ru.tinkoff.edu.java.scrapper.service.interfaces;

import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateResponse;

public interface MessageSender {
    void send(LinkUpdateResponse update);
}
