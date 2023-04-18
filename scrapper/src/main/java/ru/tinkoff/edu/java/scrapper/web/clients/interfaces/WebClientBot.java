package ru.tinkoff.edu.java.scrapper.web.clients.interfaces;

import org.springframework.http.HttpStatus;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.LinkUpdateResponse;

public interface WebClientBot {
    HttpStatus sendUpdates(LinkUpdateResponse linkUpdate);
}
