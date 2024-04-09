package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegram.interfaces.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.web.clients.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;

import java.util.Arrays;
import java.util.List;


/**
 * Class representing concrete User Message Handler. Implements UserMessageProcessor interface.
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Slf4j
public class UserMessageHandler implements UserMessageProcessor {
    private final String WARNING = "This command does not exist.\nList of available commands: /help";
    private final String SUCCESSFUL_TRACK = "The link has been successfully added!";
    private final String SUCCESSFUL_UNTRACK = "The link has been successfully deleted!";
    private final String REPLY_TRACK = "Enter a link to the repository or question you are interested in:";
    private final String REPLY_UNTRACK = "Enter the link to the repository or the question "
        + "from which updates you want to unsubscribe:";

    private final WebClientScrapper scrapperClient;

    private final List<? extends Command> commands;

    /**
     * Instantiating object with defined supported commands and defined pre-setup scrapper
     * @param scrapperClient Interface link to any Scrapper that supports WebClientScrapper interface
     * @see WebClientScrapper 
     * 
     * @param commands List of supported commands by this class
     * @see Command
     */
    public UserMessageHandler(@NotNull WebClientScrapper scrapperClient, @NotNull Command... commands) {
        this.scrapperClient = scrapperClient;
        this.commands = Arrays.stream(commands).toList();
    }

    /**
     * {@link UserMessageProcessor#commands()}
     */
    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    /**
     * {@link UserMessageProcessor#process(Update)}
     */
    @Override
    public SendMessage process(Update update) {
        for (var command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        if (isReplyTrack(update)) {
            try {
                scrapperClient.addLink(update.message().chat().id(), new AddLinkRequest(update.message().text()));
                return createSendMessage(update, SUCCESSFUL_TRACK);
            } catch (ApiErrorResponse errorResponse) {
                return createSendMessage(update, errorResponse.getDescription());
            }
        }
        if (isReplyUntrack(update)) {
            try {
                scrapperClient.deleteLink(update.message().chat().id(), new RemoveLinkRequest(update.message().text()));
                return createSendMessage(update, SUCCESSFUL_UNTRACK);
            } catch (ApiErrorResponse errorResponse) {
                return createSendMessage(update, errorResponse.getDescription());
            }
        }
        return createSendMessage(update, WARNING);
    }

    /**
     * {@link UserMessageProcessor#deleteChat(Update)}
     */
    @Override
    public void deleteChat(@NotNull Update update) {
        try {
            scrapperClient.deleteChat(update.myChatMember().chat().id());
            log.info("User: " + update.myChatMember().chat().id().toString() + " has blocked the bot.");
        } catch (ApiErrorResponse errorResponse) {
            log.warn("User: " + update.myChatMember().chat().id().toString() + " not registered.");
        }
    }

    /**
     * Method for sending messages back user based of what user input is
     * @param update Update received from user to bot
     * @param message Message to user from bot
     * @return Entity containing sent message
     */
    private SendMessage createSendMessage(@NotNull Update update, String message) {
        return new SendMessage(update.message().chat().id(), message);
    }

    /**
     * Check if bot receives command as a reply to track or untrack
     * @param update Update sent to bot from user
     * @return True if update is sent as a reply from user to bot, False otherwise
     */
    private boolean isReplyTrack(@NotNull Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(REPLY_TRACK);
    }

    /**
     * Vice versa of {@link UserMessageHandler#isReplyTrack(Update)}
     */
    private boolean isReplyUntrack(@NotNull Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(REPLY_UNTRACK);
    }
}

    