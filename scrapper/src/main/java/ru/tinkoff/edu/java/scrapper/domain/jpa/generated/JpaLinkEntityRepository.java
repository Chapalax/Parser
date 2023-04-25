package ru.tinkoff.edu.java.scrapper.domain.jpa.generated;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.LinkEntity;

@Lazy
@Repository
public interface JpaLinkEntityRepository extends JpaRepository<LinkEntity, Long> {
    Boolean existsByPath(String path);

    LinkEntity findLinkEntityByPath(String path);

    void deleteByPath(String path);
}
