package ru.tinkoff.edu.java.bot.web.clients.interfaces;

import org.springframework.http.HttpStatus;
import ru.tinkoff.edu.java.bot.web.clients.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.RemoveLinkRequest;


/**
 * Base interface for defining how bot can interact with Scrapper
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public interface WebClientScrapper {
    /**
     * Get list of all repositories attached to a client
     * @param tgChatId Telegram ID of concrete user
     * @see ListLinksResponse
     * @return Entity representing response for bot trying to get array of links of repositories from Scrapper
     */
    ListLinksResponse getAllLinks(long tgChatId);

    /**
     * Add new trackable repository to attached client
     * @param tgChatId Telegram ID of concrete user
     * @param addLinkRequest Request for adding a new repository to be tracked
     * @see AddLinkRequest
     * @see LinkResponse
     * @return Entity representing response for bot trying to add new repository to be tracked from Scrapper
     */
    LinkResponse addLink(long tgChatId, AddLinkRequest addLinkRequest);

    /**
     * Process the request for deleting concrete repository for concrete user
     * @param tgChatId Telegram ID of concrete user
     * @param removeLinkRequest Request for releting concrete repository from user-tracked repository list
     * @see RemoveLinkRequest
     * @see LinkResponse
     * @return Entity representing response for bot trying to delete repository from list of currently tracked ones from Scrapper
     */
    LinkResponse deleteLink(long tgChatId, RemoveLinkRequest removeLinkRequest);

    /**
     * Process the request for initiating new chat with concrete user
     * @param id Telegram ID of concrete user
     * @return HTTP status from Scrapper
     */
    HttpStatus registerChat(long id);

    /**
     * Process the request for deleting chat with concrete user
     * @param id Telegram ID of concrete user
     * @return HTTP status from Scrapper
     */
    HttpStatus deleteChat(long id);
}
