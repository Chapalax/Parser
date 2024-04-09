package ru.tinkoff.edu.java.linkparser.parsers;

import java.net.URI;

import ru.tinkoff.edu.java.linkparser.records.ParsedGitHub;
import ru.tinkoff.edu.java.linkparser.records.ParsedStackOverflow;

/**
 * Common interface for parser to parse sites GitHub and StackOverflow.
 * Only {@link GitHubParser} and {@link StackOverflowParser} could be implementing this interface
 * 
 * @see GitHubParser
 * @see StackOverflowParser
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public sealed interface ParserPattern permits GitHubParser, StackOverflowParser {
    /**
     * Method responsible for parsing defined URL
     * @param url URL of page to be parsed
     * @return Record of parsed page. Its type is either {@link ParsedGitHub} or {@link ParsedStackOverflow}
     */
    Record parse(URI url);
}
