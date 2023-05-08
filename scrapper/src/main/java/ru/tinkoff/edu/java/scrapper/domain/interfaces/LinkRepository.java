package ru.tinkoff.edu.java.scrapper.domain.interfaces;

import ru.tinkoff.edu.java.scrapper.domain.models.Link;

import java.util.List;

public interface LinkRepository extends Dao<Link> {
    Boolean isExists(Link link);

    Link findByUrl(Link link);

    Link findById(Link link);

    List<Link> findAllToUpdate();

    void update(Link link);
}
