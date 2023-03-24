package ru.tinkoff.edu.java.linkparser.parsers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.linkparser.records.ParsedGitHub;

import java.net.URI;

public final class GitHubParser implements ParserPattern {

    @Nullable
    @Override
    public Record parse(@NotNull URI uri) {
        String[] segments = uri.getPath().split("/");
        if (segments.length == 3) {
            String username = segments[1];
            String repository = segments[2];
            return new ParsedGitHub(username, repository);
        }
        return null;
    }
}
