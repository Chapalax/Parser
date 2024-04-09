package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;
import ru.tinkoff.edu.java.bot.web.clients.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;


/**
 * Command representing list tracked repositories between bot and user. Implements Command interface.
 * @see Command
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final String COMMAND = "list";
    private final String DESCRIPTION = "Show list of tracked links";
    private final String ANSWER = "The list of links you are tracking:\n";
    private final String WARNING = "You are not tracking any links yet!\n"
        + "To start tracking updates, use the command /track";

    private final WebClientScrapper scrapperClient;
    
    /**
     * {@link Command#command()}
     */
    @Override
    public String command() {
        return COMMAND;
    }

    /**
     * {@link Command#description()}
     */
    @Override
    public String description() {
        return DESCRIPTION;
    }

    /**
     * {@link Command#handle(Update)}
     */
    @Override
    public SendMessage handle(@NotNull Update update) {
        try {
            var links = scrapperClient.getAllLinks(update.message().chat().id());
            if (links.size() == 0) {
                return new SendMessage(update.message().chat().id(), WARNING);
            }
            var message = new StringBuilder();
            message.append(ANSWER);
            for (int i = 0; i < links.size(); i++) {
                message.append(links.links().get(i).url().toString()).append("\n");
            }
            return new SendMessage(update.message().chat().id(), message.toString());
        } catch (ApiErrorResponse errorResponse) {
            return new SendMessage(update.message().chat().id(), errorResponse.getDescription());
        }
    }
}
