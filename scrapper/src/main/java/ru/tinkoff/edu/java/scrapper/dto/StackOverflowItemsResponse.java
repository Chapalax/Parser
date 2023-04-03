package ru.tinkoff.edu.java.scrapper.dto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record StackOverflowItemsResponse(@NotNull List<StackOverflowResponse> items) {
}
