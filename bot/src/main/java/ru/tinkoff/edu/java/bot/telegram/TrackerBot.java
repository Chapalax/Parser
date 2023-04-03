package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Bot;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegram.interfaces.UserMessageProcessor;

import java.util.List;

@Component
public class TrackerBot implements Bot {
    private static TelegramBot bot;

    private static UserMessageProcessor userMessageProcessor;

    private static String token;

    @Autowired
    public TrackerBot(UserMessageProcessor messageProcessor, @Value("${app.token}") String token) {
        userMessageProcessor = messageProcessor;
        bot = new TelegramBot(token);
    }

    @Override
    public void start() {
        bot.setUpdatesListener(this);
        bot.execute(new SetMyCommands(userMessageProcessor
                .commands()
                .stream()
                .map(Command::toApiCommand)
                .toArray(BotCommand[]::new)));
    }

    @Override
    public void close() throws Exception {
        bot.removeGetUpdatesListener();
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            bot.execute(userMessageProcessor.process(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
