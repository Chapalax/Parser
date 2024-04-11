package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.telegram.UserMessageHandler;
import ru.tinkoff.edu.java.bot.telegram.commands.*;
import ru.tinkoff.edu.java.bot.telegram.interfaces.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.web.clients.interfaces.WebClientScrapper;


/**
 * Class for processing user messages with concrete parameters
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@Configuration
public class UserMessageProcessorConfig {
    @Bean
    @Autowired
    /**
     * Factory method for creating handler for user messages
     * @param scrapperClient
     * @param startCommand
     * @param helpCommand
     * @param trackCommand
     * @param untrackCommand
     * @param listCommand
     * 
     * @return 
     */
    public UserMessageProcessor userMessageProcessor (WebClientScrapper scrapperClient, StartCommand startCommand,
                                                     HelpCommand helpCommand, TrackCommand trackCommand,
                                                     UntrackCommand untrackCommand, ListCommand listCommand) {
        return new UserMessageHandler(
            scrapperClient, startCommand, helpCommand,
            trackCommand, untrackCommand, listCommand
        );
    }
}
