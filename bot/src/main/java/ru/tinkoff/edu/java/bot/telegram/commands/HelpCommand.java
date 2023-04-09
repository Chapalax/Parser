package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;

@Component
public class HelpCommand implements Command {
    private final String COMMAND = "help";
    private final String DESCRIPTION = "Show list of available commands";
    private final String ANSWER = "I can help you track updates on [GitHub repositories](https://github.com/) and " +
            "answers to [questions on StackOverflow](https://stackoverflow.com/questions/) " +
            "that interest you.\n\n" +
            "*General bot commands:*\n" +
            "/start - run the bot\n" +
            "/help - information about the bot\n\n" +
            "*Tracking:*\n" +
            "/track - subscribe to updates\n" +
            "/untrack - unsubscribe from updates\n" +
            "/list - get a list of tracked links";

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
        return new SendMessage(update.message().chat().id(), ANSWER)
                .parseMode(ParseMode.Markdown)
                .disableWebPagePreview(true);
    }
}
