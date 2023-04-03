package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;

@Slf4j
@Component
public class TrackCommand implements Command {
    @Override
    public String command() {
        return "track";
    }

    @Override
    public String description() {
        return "Start tracking link";
    }

    @Override
    public SendMessage handle(Update update) {
        // TODO: logic
        log.info("Track command log");
        return new SendMessage(update.message().chat().id(), "This command is not available now.");
    }
}
