package ru.tinkoff.edu.java.scrapper.web.clients;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.StackOverflowItemsResponse;
import ru.tinkoff.edu.java.scrapper.dto.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientStackOverflow;

import java.util.Objects;

public class StackOverflowClient implements WebClientStackOverflow {

    @Value("${baseurl.stackoverflow}")
    private String stackOverflowBaseUrl;

    private final WebClient webClient;

    public StackOverflowClient() {
        this.webClient = WebClient.create(stackOverflowBaseUrl);
    }

    public StackOverflowClient(@URL String url) {
        this.webClient = WebClient.create(url);
    }

    @Override
    public StackOverflowResponse fetchStackOverflowQuestion(String id) {
        return Objects.requireNonNull(webClient.get()
                        .uri("/questions/{id}?site=stackoverflow", id)
                        .retrieve()
                        .bodyToMono(StackOverflowItemsResponse.class)
                        .block())
                .items()
                .get(0);
    }
}
