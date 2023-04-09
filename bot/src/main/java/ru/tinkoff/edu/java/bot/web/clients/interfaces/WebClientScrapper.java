package ru.tinkoff.edu.java.bot.web.clients.interfaces;

import org.springframework.http.HttpStatus;
import ru.tinkoff.edu.java.bot.web.clients.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.RemoveLinkRequest;

public interface WebClientScrapper {
    ListLinksResponse getAllLinks(long tgChatId);

    LinkResponse addLink(long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse deleteLink(long tgChatId, RemoveLinkRequest removeLinkRequest);

    HttpStatus registerChat(long id);

    HttpStatus deleteChat(long id);
}
