package ru.tinkoff.edu.java.linkparser.parsers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
