package ru.tinkoff.edu.java.scrapper.web.clients;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.StackOverflowItemsResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientStackOverflow;

import java.util.Objects;

public class StackOverflowClient implements WebClientStackOverflow {

    @Value("${baseurl.stackoverflow}")
    private String stackOverflowBaseUrl;

    private final WebClient webClient;

    public StackOverflowClient() {
        webClient = WebClient.create(stackOverflowBaseUrl);
    }

    public StackOverflowClient(@URL String url) {
        webClient = WebClient.create(url);
    }

    @Override
    public StackOverflowResponse fetchStackOverflowQuestion(String id) {
        return Objects.requireNonNull(webClient.get()
                        .uri("/questions/{id}?site=stackoverflow", id)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse ->
                                clientResponse.bodyToMono(RuntimeException.class)
                                .flatMap(error -> Mono.error(new RuntimeException("StackOverflow API Exception"))))
                        .bodyToMono(StackOverflowItemsResponse.class)
                        .block())
                .items()
                .get(0);
    }
}
