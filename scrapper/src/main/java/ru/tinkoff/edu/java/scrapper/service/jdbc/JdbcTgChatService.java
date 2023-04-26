package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exceptions.RegisteredUserExistsException;
import ru.tinkoff.edu.java.scrapper.models.TgChat;
import ru.tinkoff.edu.java.scrapper.service.interfaces.TgChatService;

@Service
public class JdbcTgChatService implements TgChatService {

    private final TgChatRepository tgChatRepository;

    @Autowired
    public JdbcTgChatService(TgChatRepository tgChatRepository) {
        this.tgChatRepository = tgChatRepository;
    }

    @Override
    public void register(long tgChatId) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        if(tgChatRepository.isExists(chat)) throw new RegisteredUserExistsException("You're already registered");
        tgChatRepository.add(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        if(tgChatRepository.remove(chat) == 0) throw new ChatNotFoundException("Chat not found.");
    }
}
