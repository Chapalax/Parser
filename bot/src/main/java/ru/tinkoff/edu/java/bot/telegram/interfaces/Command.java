package ru.tinkoff.edu.java.bot.telegram.interfaces;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.jetbrains.annotations.NotNull;

/**
 * Interface defining what general command from user looks like
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public interface Command {

    /**
     * Method returning text representation of the command (e.g. "start", "help")
     * @return Text naming the command
     */
    String command();

    /**
     * Method returning text description of the command (e.g. "This command does this and that")
     * @return Text describing what the command is doing
     */
    String description();

    /**
     * Method interacting with Scrapper to transform bot-request to Scrapper's defined request API
     * @param update Any update sent via chain from user to bot
     * @return Entity representing sent message to user as a result of user-input
     */
    SendMessage handle(@NotNull Update update);

    /**
     * Method checking whether sent update is supported in the application
     * @param update Any update sent from user
     * @return True if command is supported by Telegram bot model, false otherwise
     */
    default boolean supports(@NotNull Update update) {
        return command().equals(update.message().text().substring(1));
    }

    /**
     * Method transforming this command to API message known by Telegram API
     * @return API-representation of command
     */
    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
