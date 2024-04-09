package ru.tinkoff.edu.java.bot.web.clients.interfaces;

import org.springframework.http.HttpStatus;
import ru.tinkoff.edu.java.bot.web.clients.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.RemoveLinkRequest;

/**
 * Base interface for defining how scrapper can interact with Telegram API
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public interface WebClientScrapper {
    /**
     * Get list of all repositories attached to a client
     * @param tgChatId Telegram ID of concrete user
     * @see ListLinksResponse
     * @return Entity representing response for getting array of links of repositoriest and its size
     */
    ListLinksResponse getAllLinks(long tgChatId);

    /**
     * Add new trackable repository to attached client
     * @param tgChatId Telegram ID of concrete user
     * @param addLinkRequest Request for adding a new repository to be tracked
     * @see AddLinkRequest
     * @return Entity representing response for interacting with 
     */
    LinkResponse addLink(long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse deleteLink(long tgChatId, RemoveLinkRequest removeLinkRequest);

    HttpStatus registerChat(long id);

    HttpStatus deleteChat(long id);
}
