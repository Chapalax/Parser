package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;

@Slf4j
@Component
public class ListCommand implements Command {
    @Override
    public String command() {
        return "list";
    }

    @Override
    public String description() {
        return "Show list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        // TODO: logic
        log.info("List command log");
        if (Math.random() * 10 > 5) {
            return new SendMessage(update.message().chat().id(), "You are not tracking any links yet!\n" +
                    "To start tracking updates, use the command /track");
        }
        return new SendMessage(update.message().chat().id(), "This command is not available now.");
    }
}
