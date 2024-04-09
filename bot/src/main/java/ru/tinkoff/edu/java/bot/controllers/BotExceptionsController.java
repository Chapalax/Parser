package ru.tinkoff.edu.java.bot.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


/**
 * Controller that handles exceptions that bot has sent it.
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@RestControllerAdvice
public class BotExceptionsController {

    /**
     * Creae error based of what bot sent to controller
     * @param exception exception generated from bot
     * @param description description of exception generated from bot
     * @param httpStatus http status received from bot
     * @return ApiErrorResponse for client
     */
    private ApiErrorResponse createError(@NotNull Throwable exception, String description, HttpStatus httpStatus) {
        ArrayList<String> stacktrace = new ArrayList<>(exception.getStackTrace().length);
        for (StackTraceElement line : exception.getStackTrace()) {
            stacktrace.add(line.toString());
        }
        return new ApiErrorResponse(
            description, Integer.toString(httpStatus.value()),
            httpStatus.getReasonPhrase(), exception.getMessage(), stacktrace
        );
    }

    /**
     * Generate HTTP payload saying that arguments from client are invalid
     * @param error HTTP status indicating that client's argument is invalid (400 to 499)
     * @return HTTP payload
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> argumentNotValid(@NotNull MethodArgumentNotValidException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect URL", BAD_REQUEST));
    }

    /**
     *  Generate HTTP payload saying that media from client is invalid
     * @param error HTTP status indicating that client's media is invalid (415)
     * @return HTTP payload
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> mediaTypeNotSupported(@NotNull HttpMediaTypeNotSupportedException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect Media Type", BAD_REQUEST));
    }

    /**
     *  Generate HTTP payload saying that HTTP request from client could have been parsed
     * @param error HTTP status indicating that HTTP request from client could have been parsed (422)
     * @return HTTP payload
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> httpMessageNotReadable(@NotNull HttpMessageNotReadableException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect Request Body", BAD_REQUEST));
    }

    /**
     *  Generate HTTP payload to any other exceptions
     * @param error HTTP status indicating that something went wrong (500)
     * @return HTTP payload
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> otherException(@NotNull Exception error) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(createError(error, "Server Exception", INTERNAL_SERVER_ERROR));
    }
}
