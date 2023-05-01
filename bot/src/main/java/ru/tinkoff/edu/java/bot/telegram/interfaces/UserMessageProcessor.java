package ru.tinkoff.edu.java.bot.telegram.interfaces;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public interface UserMessageProcessor {
    List<? extends Command> commands();

    SendMessage process(Update update);

    void deleteChat(Update update);
}
