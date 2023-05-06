package ru.tinkoff.edu.java.bot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.telegram.consumers.ScrapperQueueListener;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
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
    public Queue deadLetterQueue(@NotNull ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.queueName() + ".dlq").build();
    }

    @Bean
    public Binding binding(@NotNull Queue queue, @NotNull DirectExchange directExchange) {
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .withQueueName();
    }

    @Bean
    public Binding deadLetterBinding(@NotNull Queue deadLetterQueue, @NotNull DirectExchange directExchange) {
        return BindingBuilder
                .bind(deadLetterQueue)
                .to(directExchange)
                .with(deadLetterQueue.getName() + ".dlq");
    }

    @Bean
    public ScrapperQueueListener scrapperQueueListener() {
        return new ScrapperQueueListener();
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.LinkUpdateResponse", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}