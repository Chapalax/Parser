package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegram.interfaces.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.web.clients.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;

import java.util.Arrays;
import java.util.List;

public class UserMessageHandler implements UserMessageProcessor {
    private final String WARNING = "This command does not exist.\nList of available commands: /help";
    private final String SUCCESSFUL_TRACK = "The link has been successfully added!";
    private final String SUCCESSFUL_UNTRACK = "The link has been successfully deleted!";
    private final String ERROR_MESSAGE = "Something went wrong, try again.";
    private final String REPLY_TRACK = "Enter a link to the repository or question you are interested in:";
    private final String REPLY_UNTRACK = "Enter the link to the repository or the question " +
            "from which updates you want to unsubscribe:";

    private final WebClientScrapper scrapperClient;

    private final List<? extends Command> commands;

    public UserMessageHandler(WebClientScrapper scrapperClient, Command ... commands) {
        this.scrapperClient = scrapperClient;
        this.commands = Arrays.stream(commands).toList();
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for(var command : commands) {
            if(command.supports(update)) return command.handle(update);
        }
        if (isReplyTrack(update)) {
            try {
                scrapperClient.addLink(update.message().chat().id(), new AddLinkRequest(update.message().text()));
                return new SendMessage(update.message().chat().id(), SUCCESSFUL_TRACK);
            } catch (ApiErrorResponse errorResponse) {
                return new SendMessage(update.message().chat().id(), ERROR_MESSAGE);
            }
        }
        if (isReplyUntrack(update)) {
            try {
                scrapperClient.deleteLink(update.message().chat().id(), new RemoveLinkRequest(update.message().text()));
                return new SendMessage(update.message().chat().id(), SUCCESSFUL_UNTRACK);
            } catch (ApiErrorResponse errorResponse) {
                return new SendMessage(update.message().chat().id(), ERROR_MESSAGE);
            }
        }
        return new SendMessage(update.message().chat().id(), WARNING);
    }

    private boolean isReplyTrack(Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(REPLY_TRACK);
    }

    private boolean isReplyUntrack(Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(REPLY_UNTRACK);
    }
}
