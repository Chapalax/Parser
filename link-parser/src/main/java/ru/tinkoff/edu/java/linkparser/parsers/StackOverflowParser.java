package ru.tinkoff.edu.java.linkparser.parsers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.linkparser.records.ParsedStackOverflow;

import java.net.URI;

public final class StackOverflowParser implements ParserPattern {

    @Nullable
    @Override
    public Record parse(@NotNull URI uri) {
        String[] segments = uri.getPath().split("/");
        if (segments.length >= 3 && "questions".equals(segments[1])) {
            return new ParsedStackOverflow(segments[2]);
        }
        return null;
    }
}
