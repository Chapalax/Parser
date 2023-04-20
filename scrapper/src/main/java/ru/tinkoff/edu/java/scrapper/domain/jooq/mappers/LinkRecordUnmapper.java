package ru.tinkoff.edu.java.scrapper.domain.jooq.mappers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.Links;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.records.LinksRecord;
import ru.tinkoff.edu.java.scrapper.models.Link;

@Component
@RequiredArgsConstructor
public class LinkRecordUnmapper implements RecordUnmapper<Link, LinksRecord> {

    private final DSLContext dsl;

    @Override
    public @NotNull LinksRecord unmap(Link link) throws MappingException {
        LinksRecord record = dsl.newRecord(Links.LINKS, link);
        if(link.getLastActivity() != null) record.setLastActivity(link.getLastActivity().toLocalDateTime());
        if(link.getCheckedAt() != null) record.setCheckedAt(link.getCheckedAt().toLocalDateTime());
        return record;
    }
}
