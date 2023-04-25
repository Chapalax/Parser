package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;
import ru.tinkoff.edu.java.bot.web.clients.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final String COMMAND = "start";
    private final String DESCRIPTION = "Run the bot";
    private final String ERROR = "Something went wrong, try again later.";
    private final String ANSWER = "Thank you for using our bot!\n" +
            "To get information about the bot, use the command /help";

    private final WebClientScrapper scrapperClient;

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            scrapperClient.registerChat(update.message().chat().id());
            return new SendMessage(update.message().chat().id(), ANSWER);
        } catch (ApiErrorResponse errorResponse) {
            return new SendMessage(update.message().chat().id(),ERROR);
        }
    }
}
