package ru.tinkoff.edu.java.scrapper.domain.jooq.mappers;


import org.jetbrains.annotations.Nullable;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.jooq.generated.tables.records.LinksRecord;
import ru.tinkoff.edu.java.scrapper.models.Link;

@Component
public class LinkRecordMapper implements RecordMapper<LinksRecord, Link> {
    @Override
    public @Nullable Link map(LinksRecord linksRecord) {
        Link link = linksRecord.into(Link.class);
        link.setLastActivity(link.getLastActivity().toZonedDateTime().toOffsetDateTime());
        link.setCheckedAt(link.getCheckedAt().toZonedDateTime().toOffsetDateTime());
        return link;
    }
}
