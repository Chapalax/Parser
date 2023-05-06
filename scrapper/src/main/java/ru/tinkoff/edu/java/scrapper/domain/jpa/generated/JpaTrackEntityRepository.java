package ru.tinkoff.edu.java.scrapper.domain.jpa.generated;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.TrackEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.TrackPrimaryKey;

import java.util.List;

@Lazy
@Repository
public interface JpaTrackEntityRepository extends JpaRepository<TrackEntity, TrackPrimaryKey> {
    Boolean existsByLinkId(Long id);

    List<TrackEntity> findAllByChatId(Long id);

    List<TrackEntity> findAllByLinkId(Long id);
}
