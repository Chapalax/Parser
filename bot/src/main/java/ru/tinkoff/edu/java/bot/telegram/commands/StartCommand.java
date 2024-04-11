package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;
import ru.tinkoff.edu.java.bot.web.clients.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;

/**
 * Command initializing contact between bot and user. Implements Command interface.
 * @see Command
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final String COMMAND = "start";
    private final String DESCRIPTION = "Run the bot";
    private final String ANSWER = "Thank you for using our bot!\n"
        + "To get information about the bot, use the command /help";

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
            scrapperClient.registerChat(update.message().chat().id());
            return new SendMessage(update.message().chat().id(), ANSWER);
        } catch (ApiErrorResponse errorResponse) {
            return new SendMessage(update.message().chat().id(), errorResponse.getDescription());
        }
    }
}
