package ru.tinkoff.edu.java.scrapper.domain.jpa.mappers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.TgChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;

@Component
public class TgChatEntityMapper {
    public TgChat map(@NotNull TgChatEntity entity) {
        TgChat tgChat = new TgChat();
        tgChat.setId(entity.getId());
        return tgChat;
    }

    public @NotNull TgChatEntity unmap(@NotNull TgChat tgChat) {
        TgChatEntity entity = new TgChatEntity();
        entity.setId(tgChat.getId());
        return entity;
    }
}
