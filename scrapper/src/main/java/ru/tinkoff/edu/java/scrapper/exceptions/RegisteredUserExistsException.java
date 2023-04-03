package ru.tinkoff.edu.java.scrapper.exceptions;

public class RegisteredUserExistsException extends RuntimeException {
    public RegisteredUserExistsException(String message) {
        super(message);
    }
}
