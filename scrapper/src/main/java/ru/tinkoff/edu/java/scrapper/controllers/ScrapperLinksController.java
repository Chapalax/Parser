package ru.tinkoff.edu.java.scrapper.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.service.interfaces.LinkService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class ScrapperLinksController {

    private final LinkService linkService;

    @GetMapping
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) {
        List<Link> linkList = linkService.listAll(tgChatId);
        ArrayList<LinkResponse> responseArrayList = new ArrayList<>();
        for (Link link : linkList) {
            responseArrayList.add(new LinkResponse(link.getId(), link.getPath()));
        }
        return new ResponseEntity<>(new ListLinksResponse(responseArrayList, responseArrayList.size()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LinkResponse> postAddLink(@RequestHeader("Tg-Chat-Id") long tgChatId,
                                                    @RequestBody @Valid @NotNull AddLinkRequest addLinkRequest) {
        Link addedLink = linkService.add(tgChatId, URI.create(addLinkRequest.link()));
        return new ResponseEntity<>(new LinkResponse(addedLink.getId(), addedLink.getPath()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> deleteLink(@RequestHeader("Tg-Chat-Id") long tgChatId,
                                                   @RequestBody @Valid @NotNull RemoveLinkRequest removeLinkRequest) {
        Link link = linkService.remove(tgChatId, URI.create(removeLinkRequest.link()));
        return new ResponseEntity<>(new LinkResponse(link.getId(), link.getPath()), HttpStatus.OK);
    }
}
