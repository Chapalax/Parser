package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.telegram.UserMessageHandler;
import ru.tinkoff.edu.java.bot.telegram.commands.*;
import ru.tinkoff.edu.java.bot.telegram.interfaces.UserMessageProcessor;

@Configuration
public class UserMessageProcessorConfig {
    @Bean
    public UserMessageProcessor userMessageProcessor(StartCommand startCommand, HelpCommand helpCommand,
                                                     TrackCommand trackCommand, UntrackCommand untrackCommand,
                                                     ListCommand listCommand) {
        return new UserMessageHandler(startCommand, helpCommand, trackCommand, untrackCommand, listCommand);
    }
}
