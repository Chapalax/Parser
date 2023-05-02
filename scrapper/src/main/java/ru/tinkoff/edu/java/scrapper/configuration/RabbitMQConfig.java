package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.service.interfaces.MessageSender;
import ru.tinkoff.edu.java.scrapper.service.producers.ScrapperQueueProducer;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMQConfig {

    @Bean
    public DirectExchange directExchange(@NotNull ApplicationConfig applicationConfig) {
        log.info("Creating RabbitMQ beans... ");
        return new DirectExchange(applicationConfig.exchangeName());
    }

    @Bean
    public Queue queue(@NotNull ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.queueName())
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", applicationConfig.queueName() + ".dlq")
                .build();
    }

    @Bean
    public Binding binding(@NotNull Queue queue, @NotNull DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .withQueueName();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageSender messageSender(@NotNull RabbitTemplate rabbitTemplate,
                                       @NotNull ApplicationConfig applicationConfig) {
        log.info("Creating QueueProducer bean...");
        return new ScrapperQueueProducer(rabbitTemplate, applicationConfig);
    }
}
