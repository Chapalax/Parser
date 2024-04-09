package ru.tinkoff.edu.java.bot.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.telegram.TrackerBot;


/**
 * Controller for handling /updates endpoint
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@RestController
@RequestMapping("/updates")
public class BotUpdatesController {

    /**
     * @param linkUpdate - link to which bot sends updates to user of user's chosen repo 
     * @return - HTTP status 200
     */
    @PostMapping
    public ResponseEntity<HttpStatus> postSendUpdate(@RequestBody @Valid LinkUpdateRequest linkUpdate) {
        TrackerBot.sendUpdates(linkUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}


