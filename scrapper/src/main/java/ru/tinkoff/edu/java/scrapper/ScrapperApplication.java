package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.configuration.ClientConfiguration;
import ru.tinkoff.edu.java.scrapper.web.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.web.clients.StackOverflowClient;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, ClientConfiguration.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig appConfig = ctx.getBean(ApplicationConfig.class);
        ClientConfiguration clientConfig= ctx.getBean(ClientConfiguration.class);
        System.out.println(appConfig);
        System.out.println(clientConfig);
        var gitHubClient = ctx.getBean(GitHubClient.class, clientConfig.github());
        System.out.println(gitHubClient.fetchGitHubRepository("Chapalax", "Parser"));
        var stackOverflowClient = ctx.getBean(StackOverflowClient.class, clientConfig.stackOverflowClient());
        System.out.println(stackOverflowClient.fetchStackOverflowQuestion("4963300"));
    }
}