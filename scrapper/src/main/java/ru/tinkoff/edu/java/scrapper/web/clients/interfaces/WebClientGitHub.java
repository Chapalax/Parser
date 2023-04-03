package ru.tinkoff.edu.java.scrapper.web.clients.interfaces;

import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;

public interface WebClientGitHub {
    GitHubResponse fetchGitHubRepository(String user, String repository);
}
