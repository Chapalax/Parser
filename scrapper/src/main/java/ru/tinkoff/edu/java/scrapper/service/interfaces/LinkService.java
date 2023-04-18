package ru.tinkoff.edu.java.scrapper.service.interfaces;

import org.jetbrains.annotations.NotNull;
import ru.tinkoff.edu.java.scrapper.models.Link;

import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(long tgChatId, @NotNull URI url);

    Link remove(long tgChatId, @NotNull URI url);

    List<Link> listAll(long tgChatId);
}
