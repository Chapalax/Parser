package ru.tinkoff.edu.java.linkparser.parsers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.linkparser.records.ParsedGitHub;

import java.net.URI;

/**
 * Concrete implementation of ParserPattern interface responsible for parsing GitHub pages. 
 * @see ParserPattern
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public final class GitHubParser implements ParserPattern {

    /**
     * Implementation of parsing GitHub page
     * @param uri Given URI
     * @return If error occures, returns null. Otherwise, {@link ParsedGitHub} record with username of repository and repository name.
     */
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
