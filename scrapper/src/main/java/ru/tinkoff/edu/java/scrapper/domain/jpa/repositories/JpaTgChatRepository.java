package ru.tinkoff.edu.java.scrapper.domain.jpa.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.generated.JpaTgChatEntityRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.mappers.TgChatEntityMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;

import java.util.List;

@RequiredArgsConstructor
public class JpaTgChatRepository implements TgChatRepository {

    @Lazy
    private final JpaTgChatEntityRepository tgChatEntityRepository;

    private final TgChatEntityMapper mapper = new TgChatEntityMapper();

    @Override
    @Transactional
    public TgChat add(@NotNull TgChat object) {
        return mapper.map(tgChatEntityRepository.save(mapper.unmap(object)));
    }

    @Override
    @Transactional
    public int remove(@NotNull TgChat object) {
        if (tgChatEntityRepository.existsById(object.getId())) {
            tgChatEntityRepository.delete(mapper.unmap(object));
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public List<TgChat> findAll() {
        return tgChatEntityRepository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    @Transactional
    public Boolean isExists(@NotNull TgChat chat) {
        return tgChatEntityRepository.existsById(chat.getId());
    }

    public void flush() {
        tgChatEntityRepository.flush();
    }
}
