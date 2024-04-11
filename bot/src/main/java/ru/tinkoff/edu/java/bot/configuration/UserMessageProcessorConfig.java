package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.telegram.UserMessageHandler;
import ru.tinkoff.edu.java.bot.telegram.commands.*;
import ru.tinkoff.edu.java.bot.telegram.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegram.interfaces.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;


/**
 * Class for processing user messages with concrete parameters
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Configuration
public class UserMessageProcessorConfig {

    /**
     * Factory method for creating handler for user messages. User commands that can be sent to bot are pre-setup. User commands are inherited from {@link Command} interface.
     * @param scrapperClient Pre-setup scrapper client
     * @param startCommand Pre-setup Start the bot command
     * @param helpCommand Pre-setup Help the user command
     * @param trackCommand Pre-setup Track the repository command
     * @param untrackCommand Pre-setup Untrack the repository command
     * @param listCommand Pre-setup Show me the commands command
     * 
     * @return Instance of UserMessageProcessor that can handle the commands defined in arguments
     */

    @Bean
    @Autowired
    public UserMessageProcessor userMessageProcessor (WebClientScrapper scrapperClient, StartCommand startCommand,
                                                     HelpCommand helpCommand, TrackCommand trackCommand,
                                                     UntrackCommand untrackCommand, ListCommand listCommand) {
        return new UserMessageHandler(
            scrapperClient, startCommand, helpCommand,
            trackCommand, untrackCommand, listCommand
        );
    }
}
