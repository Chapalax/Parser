package ru.tinkoff.edu.java.scrapper.domain.jooq.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.Chats;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;

import java.util.List;

@RequiredArgsConstructor
public class JooqTgChatRepository implements TgChatRepository {

    private final DSLContext dsl;

    @Override
    @Transactional
    public TgChat add(@NotNull TgChat object) {
        return dsl.insertInto(Chats.CHATS)
                .set(dsl.newRecord(Chats.CHATS, object))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + object.getId()))
                .into(TgChat.class);
    }

    @Override
    @Transactional
    public int remove(@NotNull TgChat object) {
        return dsl.deleteFrom(Chats.CHATS)
                .where(Chats.CHATS.ID.eq(object.getId()))
                .execute();
    }

    @Override
    @Transactional
    public List<TgChat> findAll() {
        return dsl.selectFrom(Chats.CHATS)
                .stream()
                .map(chatsRecord -> {
                    TgChat chat = new TgChat();
                    chat.setId(chatsRecord.getId());
                    return chat;
                })
                .toList();
    }

    @Override
    @Transactional
    public Boolean isExists(@NotNull TgChat chat) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(Chats.CHATS)
                        .where(Chats.CHATS.ID.eq(chat.getId()))
        );
    }
}
