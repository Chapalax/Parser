package ru.tinkoff.edu.java.bot.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@RestController
@RequestMapping("/updates")
public class BotUpdatesController {

    @PostMapping
    public ResponseEntity<HttpStatus> postSendUpdate(@RequestBody @Valid LinkUpdateRequest linkUpdate){
        try {
            // TODO logic
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e){
            // TODO throw
            throw new RuntimeException();
        }
    }
}


