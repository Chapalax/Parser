package ru.tinkoff.edu.java.bot.web.clients;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.web.clients.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;

public class ScrapperClient implements WebClientScrapper {
    private final String REQUEST_HEADER = "Tg-Chat-Id";

    @Value("${baseurl.scrapper}")
    private String baseUrl;

    private final WebClient webClient;

    public ScrapperClient() {
        webClient = WebClient.create(baseUrl);
    }

    public ScrapperClient(@URL String url) {
        webClient = WebClient.create(url);
    }

    @Override
    public ListLinksResponse getAllLinks(long tgChatId) {
        return webClient.get()
                .uri("/links")
                .header(REQUEST_HEADER, String.valueOf(tgChatId))
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
    }

    @Override
    public LinkResponse addLink(long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
                .uri("/links")
                .header(REQUEST_HEADER, String.valueOf(tgChatId))
                .bodyValue(addLinkRequest)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    @Override
    public LinkResponse deleteLink(long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
                .uri("/links")
                .header(REQUEST_HEADER, String.valueOf(tgChatId))
                .bodyValue(removeLinkRequest)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    @Override
    public HttpStatus registerChat(long id) {
        return webClient.post()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(HttpStatus.class)
                .block();
    }

    @Override
    public HttpStatus deleteChat(long id) {
        return webClient.delete()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(HttpStatus.class)
                .block();
    }
}
