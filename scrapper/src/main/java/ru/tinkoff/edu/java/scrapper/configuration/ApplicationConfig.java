package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.configuration.connection.AccessType;
import ru.tinkoff.edu.java.scrapper.schedule.Scheduler;
import ru.tinkoff.edu.java.scrapper.service.interfaces.MessageSender;
import ru.tinkoff.edu.java.scrapper.service.producers.ScrapperHttpProducer;

@Slf4j
@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull Scheduler scheduler, @NotNull AccessType databaseAccessType,
                                @NotNull Boolean useQueue, @NotNull String queueName, @NotNull String exchangeName) {
    @Bean
    public long schedulerIntervalMs(@NotNull ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
    public MessageSender messageSender(@NotNull WebClientConfig webClientConfig) {
        log.info("Creating HttpProducer bean...");
        return new ScrapperHttpProducer(webClientConfig.botClient());
    }
}