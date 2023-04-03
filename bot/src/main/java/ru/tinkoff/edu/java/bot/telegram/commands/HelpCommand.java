package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;

@Component
public class HelpCommand implements Command {
    @Override
    public String command() {
        return "help";
    }

    @Override
    public String description() {
        return "Show list of available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(),
                "I can help you track updates on [GitHub repositories](https://github.com/) and " +
                        "answers to [questions on StackOverflow](https://stackoverflow.com/questions/) " +
                        "that interest you.\n\n" +
                        "*General bot commands:*\n" +
                        "/start - run the bot\n" +
                        "/help - information about the bot\n\n" +
                        "*Tracking:*\n" +
                        "/track - subscribe to updates\n" +
                        "/untrack - unsubscribe from updates\n" +
                        "/list - get a list of tracked links")
                .parseMode(ParseMode.Markdown)
                .disableWebPagePreview(true);
    }
}
