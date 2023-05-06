package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Bot;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegram.interfaces.UserMessageProcessor;

import java.util.List;

@Component
public class TrackerBot implements Bot {
    private static TelegramBot bot;

    private final UserMessageProcessor userMessageProcessor;

    @Autowired
    public TrackerBot(UserMessageProcessor messageProcessor, @Value("${app.token}") String token) {
        userMessageProcessor = messageProcessor;
        bot = new TelegramBot(token);
    }

    @PostConstruct
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
    public void close() {
        bot.removeGetUpdatesListener();
    }

    @Override
    public int process(@NotNull List<Update> list) {
        for (Update update : list) {
            if (update.myChatMember() != null) userMessageProcessor.deleteChat(update);
            else bot.execute(userMessageProcessor.process(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public static void sendUpdates(@NotNull LinkUpdateRequest updates) {
        for (Long tgChatId : updates.tgChatIds()) {
            bot.execute(new SendMessage(
                    tgChatId,
                    "New changes in: " + updates.url() + "\n" + updates.description()));
        }
    }
}
