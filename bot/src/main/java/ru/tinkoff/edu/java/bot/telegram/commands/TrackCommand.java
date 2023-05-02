package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;

@Component
public class TrackCommand implements Command {
    private final String COMMAND = "track";
    private final String DESCRIPTION = "Start tracking link";
    private final String ANSWER = "Enter a link to the repository or question you are interested in:";
    private final String PLACEHOLDER = "https://github.com/user_name/repository_name";

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(@NotNull Update update) {
        return new SendMessage(update.message().chat().id(), ANSWER)
                .replyMarkup(new ForceReply().inputFieldPlaceholder(PLACEHOLDER));
    }
}
