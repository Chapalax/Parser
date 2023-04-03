package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.telegram.interfaces.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;

import java.util.Arrays;
import java.util.List;

public class UserMessageHandler implements UserMessageProcessor {
    private final List<? extends Command> commands;

    public UserMessageHandler(Command ... commands) {
        this.commands = Arrays.stream(commands).toList();
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for(var command : commands) {
            if(command.supports(update)) return command.handle(update);
        }
        return new SendMessage(update.message().chat().id(),
                "This command does not exist.\n" + "List of available commands: /help");
    }
}
