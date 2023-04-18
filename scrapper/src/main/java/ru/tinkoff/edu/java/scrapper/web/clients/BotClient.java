package ru.tinkoff.edu.java.scrapper.web.clients;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.LinkUpdateResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientBot;

public class BotClient implements WebClientBot {

    @Value("${baseurl.bot}")
    private String baseUrl;
    private final WebClient webClient;

    public BotClient() {
        webClient = WebClient.create(baseUrl);
    }

    public BotClient(@URL String url) {
        webClient = WebClient.create(url);
    }
    @Override
    public HttpStatus sendUpdates(LinkUpdateResponse linkUpdate) {
        return webClient.post()
                .uri("/updates")
                .bodyValue(linkUpdate)
                .retrieve()
                .bodyToMono(HttpStatus.class)
                .block();
    }
}
