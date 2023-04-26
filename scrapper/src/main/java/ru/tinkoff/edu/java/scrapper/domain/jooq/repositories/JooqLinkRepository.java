package ru.tinkoff.edu.java.scrapper.domain.jooq.repositories;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.Links;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.records.LinksRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.mappers.LinkRecordMapper;
import ru.tinkoff.edu.java.scrapper.domain.jooq.mappers.LinkRecordUnmapper;
import ru.tinkoff.edu.java.scrapper.models.Link;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {

    @Value("${check.interval}")
    private Long checkInterval;

    private final DSLContext dsl;

    private final LinkRecordMapper recordMapper;

    private final LinkRecordUnmapper recordUnmapper;

    @Override
    public Link add(Link object) {
        if (object.getActionCount() == null) object.setActionCount(0);
        return dsl.insertInto(Links.LINKS)
                .set(recordUnmapper.unmap(object))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + object.getId()))
                .map(record -> recordMapper.map((LinksRecord) record));
    }

    @Override
    public int remove(Link object) {
        return dsl.deleteFrom(Links.LINKS)
                .where(Links.LINKS.PATH.eq(object.getPath()))
                .execute();
    }

    @Override
    public List<Link> findAll() {
        return dsl.selectFrom(Links.LINKS)
                .stream()
                .map(recordMapper::map)
                .toList();
    }

    @Override
    public Boolean isExists(Link link) {
        return dsl.fetchExists(
                dsl.selectFrom(Links.LINKS)
                        .where(Links.LINKS.PATH.eq(link.getPath()))
        );
    }

    @Override
    public Link findByUrl(Link link) {
        return Objects.requireNonNull(dsl.selectOne()
                        .from(Links.LINKS)
                        .where(Links.LINKS.PATH.eq(link.getPath()))
                        .fetchOne())
                .map(record -> recordMapper.map((LinksRecord) record));
    }

    @Override
    public Link findById(Link link) {
        return Objects.requireNonNull(dsl.selectOne()
                        .from(Links.LINKS)
                        .where(Links.LINKS.ID.eq(link.getId()))
                        .fetchOne())
                .map(record -> recordMapper.map((LinksRecord) record));
    }

    @Override
    public List<Link> findAllToUpdate() {
        return findAll()
                .stream()
                .filter(link -> link
                        .getCheckedAt()
                        .isBefore(OffsetDateTime.now().minusMinutes(checkInterval)))
                .toList();
    }

    @Override
    public void update(Link link) {
        dsl.update(Links.LINKS)
                .set(recordUnmapper.unmap(link))
                .where(Links.LINKS.ID.eq(link.getId()))
                .execute();
    }
}
