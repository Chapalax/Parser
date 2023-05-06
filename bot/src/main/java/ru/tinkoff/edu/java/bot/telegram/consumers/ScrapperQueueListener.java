package ru.tinkoff.edu.java.bot.telegram.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.telegram.TrackerBot;

@RabbitListener(queues = "${app.queue-name}")
public class ScrapperQueueListener {
    @RabbitHandler
    public void receiver(LinkUpdateRequest linkUpdateRequest) {
        TrackerBot.sendUpdates(linkUpdateRequest);
    }
}
