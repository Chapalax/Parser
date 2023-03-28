package ru.tinkoff.edu.java.scrapper.exceptions;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException(String message) {
        super(message);
    }
}
