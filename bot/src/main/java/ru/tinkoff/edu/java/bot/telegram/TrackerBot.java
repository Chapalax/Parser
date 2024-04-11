package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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

/**
 * Concrete implementation of Bot
 * @see Bot
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Component
public class TrackerBot implements Bot {
    private static TelegramBot bot;

    private final UserMessageProcessor userMessageProcessor;

    private final Counter processedMessagesCounter;

    /**
     * Instantiating instance of the class with defined token
     * @param messageProcessor Pre-setup UserMessageProcessor
     * @see UserMessageProcessor
     * 
     * @param meterRegistry Container for scraping metrics and exporting them to Prometheus 
     * @see MeterRegistry
     * @param token Token assigned to bot from Telegram API
     */
    @Autowired
    public TrackerBot(@NotNull UserMessageProcessor messageProcessor, @NotNull MeterRegistry meterRegistry,
        @Value("${app.token}") String token
    ) {
        this.userMessageProcessor = messageProcessor;
        this.processedMessagesCounter = meterRegistry.counter("bot_processed_messages_counter");
        bot = new TelegramBot(token);
    }

    /**
     * Method implementing {@link Bot#start()} interface
     */
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

    /**
     * Method implementing {@link Bot#close()} interface
     */
    @Override
    public void close() {
        bot.removeGetUpdatesListener();
    }

    /**
     * Method implementing {@link Bot#process(List)} interface
     */
    @Override
    public int process(@NotNull List<Update> list) {
        for (Update update : list) {
            if (update.myChatMember() != null) {
                userMessageProcessor.deleteChat(update);
            } else {
                bot.execute(userMessageProcessor.process(update));
                processedMessagesCounter.increment();
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Method for sending messages to users about their tracked repositories
     * @param updates Received repository updates from Telegram API
     * @see LinkUpdateRequest
     */
    public static void sendUpdates(@NotNull LinkUpdateRequest updates) {
        for (Long tgChatId : updates.tgChatIds()) {
            bot.execute(new SendMessage(
                    tgChatId,
                    "New changes in: " + updates.url() + "\n" + updates.description()));
        }
    }
}
