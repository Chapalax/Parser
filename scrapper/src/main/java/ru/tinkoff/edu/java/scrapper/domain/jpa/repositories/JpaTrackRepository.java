package ru.tinkoff.edu.java.scrapper.domain.jpa.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.TrackPrimaryKey;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.generated.JpaTrackEntityRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.mappers.TrackEntityMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;
import ru.tinkoff.edu.java.scrapper.domain.models.Track;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaTrackRepository implements TrackRepository {

    private final JpaTrackEntityRepository trackEntityRepository;

    private final TrackEntityMapper mapper;

    @Override
    @Transactional
    public Track add(@NotNull Track object) {
        return mapper.map(trackEntityRepository.save(mapper.unmap(object)));
    }

    @Override
    @Transactional
    public int remove(@NotNull Track object) {
        if (trackEntityRepository.existsById(new TrackPrimaryKey(object.getChatId(), object.getLinkId()))) {
            trackEntityRepository.delete(mapper.unmap(object));
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public List<Track> findAll() {
        return trackEntityRepository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    @Transactional
    public Boolean isTracked(@NotNull TgChat chat, @NotNull Link link) {
        return trackEntityRepository.existsById(new TrackPrimaryKey(chat.getId(), link.getId()));
    }

    @Override
    @Transactional
    public Boolean isTrackedByAnyone(@NotNull Link link) {
        return trackEntityRepository.existsByLinkId(link.getId());
    }

    @Override
    @Transactional
    public List<Track> findAllTracksByUser(@NotNull TgChat chat) {
        return trackEntityRepository.findAllByChatId(chat.getId())
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    @Transactional
    public List<Track> findAllTracksWithLink(@NotNull Link link) {
        return trackEntityRepository.findAllByLinkId(link.getId())
                .stream()
                .map(mapper::map)
                .toList();
    }

    public void flush() {
        trackEntityRepository.flush();
    }
}
