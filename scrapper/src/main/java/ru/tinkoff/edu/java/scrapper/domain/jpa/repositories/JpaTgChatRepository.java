package ru.tinkoff.edu.java.scrapper.domain.jpa.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.generated.JpaTgChatEntityRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.mappers.TgChatEntityMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;

import java.util.List;

@RequiredArgsConstructor
public class JpaTgChatRepository implements TgChatRepository {

    private final JpaTgChatEntityRepository tgChatEntityRepository;

    private final TgChatEntityMapper mapper;

    @Override
    public TgChat add(@NotNull TgChat object) {
        return mapper.map(tgChatEntityRepository.save(mapper.unmap(object)));
    }

    @Override
    public int remove(@NotNull TgChat object) {
        if (tgChatEntityRepository.existsById(object.getId())) {
            tgChatEntityRepository.delete(mapper.unmap(object));
            return 1;
        }
        return 0;
    }

    @Override
    public List<TgChat> findAll() {
        return tgChatEntityRepository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public Boolean isExists(@NotNull TgChat chat) {
        return tgChatEntityRepository.existsById(chat.getId());
    }

    public void flush() {
        tgChatEntityRepository.flush();
    }
}
