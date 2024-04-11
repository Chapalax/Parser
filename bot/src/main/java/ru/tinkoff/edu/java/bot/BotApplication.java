package ru.tinkoff.edu.java.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.configuration.ScrapperClientConfig;


/**
 * Entry point to the application. Attaches defined configs to class and runs the bot with command-line arguments
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({ApplicationConfig.class, ScrapperClientConfig.class})
public class BotApplication {

    /**
     * Bot can be run with command line arguments
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }
}
