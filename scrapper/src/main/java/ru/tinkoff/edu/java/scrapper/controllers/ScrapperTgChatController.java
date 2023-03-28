package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat")
public class ScrapperTgChatController {

    @PostMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> postRegisterChat(@PathVariable("id") long id) {
        try {
            // TODO logic
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            // TODO throw
            throw new RuntimeException();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable("id") long id) {
        try {
            // TODO logic
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            // TODO throw
            throw new RuntimeException();
        }
    }
}
