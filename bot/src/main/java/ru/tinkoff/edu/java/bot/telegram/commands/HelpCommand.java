package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;


/**
 * Command representing help-command between bot and user. Implements Command interface.
 * @see Command
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Component
public class HelpCommand implements Command {
    private final String COMMAND = "help";
    private final String DESCRIPTION = "Show list of available commands";
    private final String ANSWER = "I can help you track updates on [GitHub repositories](https://github.com/) and "
        + "answers to [questions on StackOverflow](https://stackoverflow.com/questions/) "
        + "that interest you.\n\n"
        + "*General bot commands:*\n"
        + "/start - run the bot\n"
        + "/help - information about the bot\n\n"
        + "*Tracking:*\n"
        + "/track - subscribe to updates\n"
        + "/untrack - unsubscribe from updates\n"
        + "/list - get a list of tracked links";

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
        return new SendMessage(update.message().chat().id(), ANSWER)
                .parseMode(ParseMode.Markdown)
                .disableWebPagePreview(true);
    }
}
