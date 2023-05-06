package ru.tinkoff.edu.java.scrapper.service.producers;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.service.interfaces.MessageSender;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientBot;

@RequiredArgsConstructor
public class ScrapperHttpProducer implements MessageSender {

    private final WebClientBot botClient;

    @Override
    public void send(LinkUpdateResponse update) {
        botClient.sendUpdates(update);
    }
}
