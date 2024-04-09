package ru.tinkoff.edu.java.bot.telegram.interfaces;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.util.List;

/**
 * Interface for bot defining common actions of what bot can be doing
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public interface Bot extends AutoCloseable, UpdatesListener {
    /**
     * Method for starting the bot
     */
    void start();

    /**
     * Method for stopping receiving updates from users
     */
    @Override
    void close() throws Exception;

    /**
     * Method for processing the updates sent from Telegram API
     */
    @Override
    int process(List<Update> list);
}
