package ru.tinkoff.edu.java.bot.telegram.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.telegram.TrackerBot;

/**
 * Spring component that is notified when new messages in queue appear. Responsible for retranslating messages.
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@RabbitListener(queues = "${app.queue-name}")
public class ScrapperQueueListener {
    /**
     * This method is the target of a Rabbit message listener within a class (since the class is marked as @RabbitListener).
     * Listener method sends notification to bot about changes in repositories
     * @param linkUpdateRequest DTO designed for updating requests of users for given tgChatsIds with given description, url, and id of database
     * @see LinkUpdateRequest
     */
    @RabbitHandler
    public void receiver(LinkUpdateRequest linkUpdateRequest) {
        TrackerBot.sendUpdates(linkUpdateRequest);
    }
}
