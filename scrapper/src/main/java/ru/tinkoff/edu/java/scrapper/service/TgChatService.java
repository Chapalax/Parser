package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exceptions.RegisteredUserExistsException;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;

@Service
@RequiredArgsConstructor
public class TgChatService implements ru.tinkoff.edu.java.scrapper.service.interfaces.TgChatService {

    private final TgChatRepository tgChatRepository;

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
