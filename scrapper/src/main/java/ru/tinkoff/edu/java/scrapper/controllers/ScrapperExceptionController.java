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

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ScrapperExceptionController {

    private ApiErrorResponse createError(Throwable exception, String description, HttpStatus httpStatus) {
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
                .body(createError(error, "Incorrect header", BAD_REQUEST));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> httpMessageNotReadable(@NotNull HttpMessageNotReadableException error) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(createError(error, "Incorrect Request Body", BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> otherException(@NotNull Exception error) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(createError(error, "Server Exception", INTERNAL_SERVER_ERROR));
    }
}
