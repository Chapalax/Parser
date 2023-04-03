package ru.tinkoff.edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;

@Slf4j
@Component
public class StartCommand implements Command {
    @Override
    public String command() {
        return "start";
    }

    @Override
    public String description() {
        return "Run the bot";
    }

    @Override
    public SendMessage handle(Update update) {
        // TODO: logic
        log.info(update.message().chat().id() + " run the bot!");
        return new SendMessage(update.message().chat().id(), "Hello!");
    }
}
