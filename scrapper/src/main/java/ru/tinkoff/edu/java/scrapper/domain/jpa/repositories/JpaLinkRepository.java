package ru.tinkoff.edu.java.scrapper.domain.jpa.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.jpa.generated.JpaLinkEntityRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.mappers.LinkEntityMapper;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;
import ru.tinkoff.edu.java.scrapper.exceptions.LinkNotFoundException;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkRepository implements LinkRepository {

    @Value("${check.interval}")
    private Long checkInterval;

    @Lazy
    private final JpaLinkEntityRepository linkEntityRepository;

    private final LinkEntityMapper mapper = new LinkEntityMapper();

    @Override
    @Transactional
    public Link add(@NotNull Link object) {
        return mapper.map(linkEntityRepository.save(mapper.unmap(object)));
    }

    @Override
    @Transactional
    public int remove(@NotNull Link object) {
        if (linkEntityRepository.existsByPath(object.getPath())) {
            linkEntityRepository.deleteByPath(object.getPath());
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public List<Link> findAll() {
        return linkEntityRepository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    @Transactional
    public Boolean isExists(@NotNull Link link) {
        return linkEntityRepository.existsByPath(link.getPath());
    }

    @Override
    @Transactional
    public Link findByUrl(@NotNull Link link) {
        return mapper.map(linkEntityRepository.findLinkEntityByPath(link.getPath()));
    }

    @Override
    @Transactional
    public Link findById(@NotNull Link link) {
        return mapper.map(linkEntityRepository.findById(link.getId())
                .orElseThrow(() -> new LinkNotFoundException("Link not found.")));
    }

    @Override
    @Transactional
    public List<Link> findAllToUpdate() {
        return linkEntityRepository.findAllByCheckedAtBefore(OffsetDateTime.now().minusMinutes(checkInterval))
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    @Transactional
    public void update(@NotNull Link link) {
        LinkEntity entity = linkEntityRepository.findById(link.getId())
                .orElseThrow(() -> new LinkNotFoundException("Link not found."));
        entity.setActionCount(link.getActionCount());
        entity.setLastActivity(link.getLastActivity());
        entity.setCheckedAt(link.getCheckedAt());
        linkEntityRepository.save(entity);
    }

    public void flush() {
        linkEntityRepository.flush();
    }
}
