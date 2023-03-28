package ru.tinkoff.edu.java.scrapper.exceptions;

public class AddedLinkExistsException extends RuntimeException {
    public AddedLinkExistsException(String message) {
        super(message);
    }
}
