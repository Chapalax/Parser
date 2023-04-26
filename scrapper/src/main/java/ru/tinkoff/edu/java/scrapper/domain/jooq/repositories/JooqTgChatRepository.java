package ru.tinkoff.edu.java.scrapper.domain.jooq.repositories;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.Chats;
import ru.tinkoff.edu.java.scrapper.models.TgChat;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JooqTgChatRepository implements TgChatRepository {

    private final DSLContext dsl;

    @Override
    public TgChat add(TgChat object) {
        return dsl.insertInto(Chats.CHATS)
                .set(dsl.newRecord(Chats.CHATS, object))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + object.getId()))
                .into(TgChat.class);
    }

    @Override
    public int remove(TgChat object) {
        return dsl.deleteFrom(Chats.CHATS)
                .where(Chats.CHATS.ID.eq(object.getId()))
                .execute();
    }

    @Override
    public List<TgChat> findAll() {
        return dsl.selectFrom(Chats.CHATS)
                .stream()
                .map(record -> {
                    TgChat chat = new TgChat();
                    chat.setId(record.getId());
                    return chat;
                })
                .toList();
    }

    @Override
    public Boolean isExists(TgChat chat) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(Chats.CHATS)
                        .where(Chats.CHATS.ID.eq(chat.getId()))
        );
    }
}
