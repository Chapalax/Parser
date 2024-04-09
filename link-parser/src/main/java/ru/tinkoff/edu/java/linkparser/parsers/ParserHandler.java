package ru.tinkoff.edu.java.linkparser.parsers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.linkparser.records.ParsedGitHub;
import ru.tinkoff.edu.java.linkparser.records.ParsedStackOverflow;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

/**
 * Class for instantinating concrete parsing for given URL (be it StackOverflow or GitHub)
 * @see StackOverflowParser
 * @see GitHubParser
 * 
 * @author Maxim Berezhnoy
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParserHandler {
    /**
     * Method for invoking correct parser devending of what URL is inbound and then parsing it.
     * @param url URL to be parsed
     * @return Record with parsed contents. Its type is ether {@link ParsedGitHub} or {@link ParsedStackOverflow}. Otherwise, returns null.
     */
    @Nullable
    public static Record parse(@NotNull URI url) {
        return switch (url.getHost()) {
            case "stackoverflow.com" -> new StackOverflowParser().parse(url);
            case "github.com" -> new GitHubParser().parse(url);
            default -> null;
        };
    }
}
