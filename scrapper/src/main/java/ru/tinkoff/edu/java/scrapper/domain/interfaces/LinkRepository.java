package ru.tinkoff.edu.java.scrapper.domain.interfaces;

import ru.tinkoff.edu.java.scrapper.models.Link;

public interface LinkRepository extends Dao<Link>{
    Boolean isExists(Link link);

    Link findByUrl(Link link);

    Link findById(Link link);
}
