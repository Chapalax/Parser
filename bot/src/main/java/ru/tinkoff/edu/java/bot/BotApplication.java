package ru.tinkoff.edu.java.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.configuration.ScrapperClientConfig;
import ru.tinkoff.edu.java.bot.telegram.TrackerBot;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({ApplicationConfig.class, ScrapperClientConfig.class})
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        ScrapperClientConfig scrapperClientConfig = ctx.getBean(ScrapperClientConfig.class);
        System.out.println(config);
        System.out.println(scrapperClientConfig);
        ctx.getBean(TrackerBot.class).start();
    }
}