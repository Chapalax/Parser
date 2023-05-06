package ru.tinkoff.edu.java.scrapper.domain.interfaces;

import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;

public interface TgChatRepository extends Dao<TgChat> {
    Boolean isExists(TgChat chat);
}
