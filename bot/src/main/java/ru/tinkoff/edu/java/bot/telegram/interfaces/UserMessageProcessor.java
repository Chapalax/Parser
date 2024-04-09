package ru.tinkoff.edu.java.bot.telegram.interfaces;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

/**
 * Interface representing how user messages can be processed via bot
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public interface UserMessageProcessor {
    /**
     * Commands that can be applied to bot by user
     * @return List of commands
     */
    List<? extends Command> commands();

    /**
     * Method for processing user input to bot
     * @param update Message from user to bot
     * @return Message that is sent back to user as result of processing user's command
     */
    SendMessage process(Update update);

    /**
     * Dispose user from users tracked by bot
     * @param update Message from user to bot
     */
    void deleteChat(Update update);
}
