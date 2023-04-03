package ru.tinkoff.edu.java.scrapper.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

@RestController
@RequestMapping("/links")
public class ScrapperLinksController {

    @GetMapping
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) {
        try {
            // TODO logic
            return new ResponseEntity<>(new ListLinksResponse(null, 0), HttpStatus.OK);
        } catch (Exception e) {
            // TODO throw
            throw new RuntimeException();
        }
    }

    @PostMapping
    public ResponseEntity<LinkResponse> postAddLink(@RequestHeader("Tg-Chat-Id") long tgChatId,
                                                    @RequestBody @Valid AddLinkRequest addLinkRequest) {
        try {
            // TODO logic
            return new ResponseEntity<>(new LinkResponse(0, null), HttpStatus.OK);
        } catch (Exception e ) {
            // TODO throw
            throw new RuntimeException();
        }
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> deleteLink(@RequestHeader("Tg-Chat-Id") long tgChatId,
                                                   @RequestBody @Valid RemoveLinkRequest removeLinkRequest) {
        try {
            // TODO logic
            return new ResponseEntity<>(new LinkResponse(0, null), HttpStatus.OK);
        } catch (Exception e) {
            // TODO throw
            throw new RuntimeException();
        }
    }
}
