package ru.tinkoff.edu.java.scrapper.service.producers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.service.interfaces.MessageSender;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateResponse;

@RequiredArgsConstructor
public class ScrapperQueueProducer implements MessageSender {

    private final RabbitTemplate rabbitTemplate;

    private final ApplicationConfig applicationConfig;

    @Override
    public void send(LinkUpdateResponse update) {
        rabbitTemplate.convertAndSend(applicationConfig.exchangeName(), applicationConfig.queueName(), update);
    }
}
