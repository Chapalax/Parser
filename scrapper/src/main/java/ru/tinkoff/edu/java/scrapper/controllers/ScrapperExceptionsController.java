package ru.tinkoff.edu.java.scrapper.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exceptions.AddedLinkExistsException;
import ru.tinkoff.edu.java.scrapper.exceptions.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exceptions.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.exceptions.RegisteredUserExistsException;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ScrapperExceptionsController {

    // TODO: fix

    private ApiErrorResponse createError(@NotNull Throwable exception, String description, HttpStatus httpStatus) {
        ArrayList<String> stacktrace = new ArrayList<>(exception.getStackTrace().length);
        for (StackTraceElement line : exception.getStackTrace()) stacktrace.add(line.toString());
        return new ApiErrorResponse(description, Integer.toString(httpStatus.value()), httpStatus.getReasonPhrase(),
                exception.getMessage(),stacktrace);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> argumentTypeMismatch(@NotNull MethodArgumentTypeMismatchException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect Argument Type", BAD_REQUEST));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> mediaTypeNotSupported(@NotNull HttpMediaTypeNotSupportedException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect Media Type", BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> argumentNotValid(@NotNull MethodArgumentNotValidException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect URL", BAD_REQUEST));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiErrorResponse> missingRequestHeader(@NotNull MissingRequestHeaderException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect Header", BAD_REQUEST));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> httpMessageNotReadable(@NotNull HttpMessageNotReadableException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect Request Body", BAD_REQUEST));
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> chatNotFound(@NotNull ChatNotFoundException error) {
        return ResponseEntity.status(NOT_FOUND)
                .body(createError(error, "Chat Not Found", NOT_FOUND));
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> linkNotFound(@NotNull LinkNotFoundException error) {
        return ResponseEntity.status(NOT_FOUND)
                .body(createError(error, "Link Not Found", NOT_FOUND));
    }

    @ExceptionHandler(AddedLinkExistsException.class)
    public ResponseEntity<ApiErrorResponse> addedLinkExists(@NotNull AddedLinkExistsException error) {
        return ResponseEntity.status(METHOD_NOT_ALLOWED)
                .body(createError(error, "This Link Has Already Been Added", METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(RegisteredUserExistsException.class)
    public HttpStatus registeredUserExists(@NotNull RegisteredUserExistsException error) {
        return OK;
        // return ResponseEntity.status(OK)
        //        .body(createError(error, "This User Is Already Registered", METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> otherException(@NotNull Exception error) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(createError(error, "Server Exception", INTERNAL_SERVER_ERROR));
    }
}
