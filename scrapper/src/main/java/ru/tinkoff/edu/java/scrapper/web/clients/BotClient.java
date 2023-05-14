package ru.tinkoff.edu.java.scrapper.web.clients;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.ApiErrorResponse;
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
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                        .flatMap(error -> Mono.error(new ApiErrorResponse(error.getDescription(), error.getCode(),
                                error.getExceptionName(), error.getExceptionMessage(), error.getStacktrace()))))
                .bodyToMono(HttpStatus.class)
                .block();
    }
}
