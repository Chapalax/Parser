package ru.tinkoff.edu.java.linkparser.records;

/**
 * DTO representing parsed GitHub page of repository
 * @param user User to which repository belongs
 * @param repository Repository name
 * 
 * @author Maxim Berezhnoy
 * @version 1.0
 */
public record ParsedGitHub(String user, String repository) {
}
