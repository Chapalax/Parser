package ru.tinkoff.edu.java.scrapper.domain.jpa.mappers;

import org.jetbrains.annotations.NotNull;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entities.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;

public class LinkEntityMapper {
    public Link map(@NotNull LinkEntity entity) {
        Link link = new Link();
        link.setId(entity.getId());
        link.setPath(entity.getPath());
        link.setLastActivity(entity.getLastActivity());
        link.setActionCount(entity.getActionCount());
        link.setCheckedAt(entity.getCheckedAt());
        return link;
    }

    public @NotNull LinkEntity unmap(@NotNull Link link) {
        LinkEntity entity = new LinkEntity();
        entity.setPath(link.getPath());
        entity.setLastActivity(link.getLastActivity());
        entity.setActionCount(link.getActionCount());
        entity.setCheckedAt(link.getCheckedAt());
        return entity;
    }
}
