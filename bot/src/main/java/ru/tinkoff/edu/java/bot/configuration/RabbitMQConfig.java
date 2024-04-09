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

/**
 * Class used for setting up RabbitMQ config.
 * @author Maxim Berezhnoy
 * @version 1.0
 */

@Slf4j
@Configuration
public class RabbitMQConfig {

    /**
     * Method responsible for instantiating a container describing a direct exchange
     * @param applicationConfig Instance of pre-setup application configuration. Logs information about setting up RabbitMQ direct exchange beans.
     * @see ApplicationConfig
     * @return Simple container collecting information to describe a direct exchange. Used in conjunction with administrative operations.
     */
    @Bean
    public DirectExchange directExchange(@NotNull ApplicationConfig applicationConfig) {
        log.info("Creating RabbitMQ beans... ");
        return new DirectExchange(applicationConfig.exchangeName());
    }

    /**
     * Method responsible for instantiating message queue between bot and clients
     * @param applicationConfig Instance of pre-setup application configuration. Logs information about setting up RabbitMQ direct exchange beans.
     * @see ApplicationConfig
     * @return Simple container collecting information to describe a queue. Used in conjunction with AmqpAdmin.
     */
    @Bean
    public Queue queue(@NotNull ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.queueName())
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", applicationConfig.queueName() + ".dlq")
                .build();
    }

    /**
     * Method responsible for instantiating queue of unsent messages between bot and clients
     * @param applicationConfig Instance of pre-setup application configuration. Logs information about setting up RabbitMQ direct exchange beans.
     * @see ApplicationConfig 
     * @return A durable queue with a generated unique name based of ApplicationConfig queue's setup name.
     */
    @Bean
    public Queue deadLetterQueue(@NotNull ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.queueName() + ".dlq").build();
    }

    /**
     * Method responsible to bind the pre-setup queue with exchange type (e.g. Direct, Headers, Consistent-Hashing)
     * @param queue Pre-setup Queue container
     * @param directExchange Pre-setup Queue Exchange type
     * @return Binding between existing queue and its Exchange type (the queue is interested in messages from this exchange)
     */
    @Bean
    public Binding binding(@NotNull Queue queue, @NotNull DirectExchange directExchange) {
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .withQueueName();
    }

    /**
     * Method responsible to bind the pre-setup queue with unsent messages with exchange type (e.g. Direct, Headers, Consistent-Hashing)
     * @param deadLetterQueue Pre-setup container of Queue of unsent messages
     * @param directExchange Queue Exchange type selected (direct in this context)
     * @return Binding between existing queue of unsent messages and its Exchange type (the queue is interested in messages from this exchange)
     */
    @Bean
    public Binding deadLetterBinding(@NotNull Queue deadLetterQueue, @NotNull DirectExchange directExchange) {
        return BindingBuilder
                .bind(deadLetterQueue)
                .to(directExchange)
                .with(deadLetterQueue.getName() + ".dlq");
    }

    /**
     * Builder method responsible for instantiating a spring component that is notified when new messages in the queue appear. Responsible for retranslating messages.
     * @see ScrapperQueueListener
     * @return Instance of ScrapperQueueListener
     */
    @Bean
    public ScrapperQueueListener scrapperQueueListener() {
        return new ScrapperQueueListener();
    }

    /**
     * Builder method responsible for instantiating a mapper between objects in the queue and Java objects
     * @return Instance of class mapper
     */
    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.LinkUpdateResponse", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    /**
     * Builder method responsible for instantiating a JSON-Java converter between objects in selected classMapper
     * @param classMapper - Class mapper set up previously, for more details see {@link #classMapper()}
     * @return Instance of message converter (Json to Java objects)
     */
    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
