package ru.tinkoff.edu.java.linkparser.parsers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.linkparser.records.ParsedStackOverflow;

import java.net.URI;


/**
 * Concrete implementation of {@link ParserPattern} interface responsible for parsing StackOverflow pages. 
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public final class StackOverflowParser implements ParserPattern {

    /**
     * Implementation of parsing StackOverflow page
     * @param uri Given URI
     * @return If error occures, returns null. Otherwise, {@link ParsedStackOverflow} record with username of repository and repository name.
     */
    @Nullable
    @Override
    public Record parse(@NotNull URI uri) {
        String[] segments = uri.getPath().split("/");
        if (segments.length >= 3 && "questions".equals(segments[1])) {
            try {
                Long.parseLong(segments[2]);
            } catch (NumberFormatException e) {
                return null;
            }
            return new ParsedStackOverflow(segments[2]);
        }
        return null;
    }
}
