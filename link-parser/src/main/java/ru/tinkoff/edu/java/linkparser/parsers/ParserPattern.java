package ru.tinkoff.edu.java.linkparser.parsers;

import java.net.URI;

public sealed interface ParserPattern permits GitHubParser, StackOverflowParser {
    Record parse(URI url);
}
