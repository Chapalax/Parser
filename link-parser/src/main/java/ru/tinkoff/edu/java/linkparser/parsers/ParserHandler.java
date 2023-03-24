package ru.tinkoff.edu.java.linkparser.parsers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

public final class ParserHandler {

    @Nullable
    public static Record parse(@NotNull URI url) {
        return switch (url.getHost()) {
            case "stackoverflow.com" -> new StackOverflowParser().parse(url);
            case "github.com" -> new GitHubParser().parse(url);
            default -> null;
        };
    }
}
