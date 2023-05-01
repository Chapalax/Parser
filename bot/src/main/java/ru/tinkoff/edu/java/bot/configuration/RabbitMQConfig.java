package ru.tinkoff.edu.java.bot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.telegram.consumers.ScrapperQueueListener;

import java.util.HashMap;
import java.util.Map;

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
        return new Queue(applicationConfig.queueName());
    }

    @Bean
    public Binding binding(@NotNull Queue queue, @NotNull DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .withQueueName();
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