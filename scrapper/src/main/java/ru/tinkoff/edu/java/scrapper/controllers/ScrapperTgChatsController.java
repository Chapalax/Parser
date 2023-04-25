package ru.tinkoff.edu.java.scrapper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.service.interfaces.TgChatService;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class ScrapperTgChatsController {

    private final TgChatService tgChatService;

    @PostMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> postRegisterChat(@PathVariable("id") long id) {
        tgChatService.register(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable("id") long id) {
        tgChatService.unregister(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
