package ru.tinkoff.edu.java.scrapper.domain.jpa.generated;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.TgChatEntity;

@Lazy
@Repository
public interface JpaTgChatEntityRepository extends JpaRepository<TgChatEntity, Long> {
}
