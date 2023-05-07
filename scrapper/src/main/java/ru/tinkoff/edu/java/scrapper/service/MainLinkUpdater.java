package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkparser.parsers.ParserHandler;
import ru.tinkoff.edu.java.linkparser.records.ParsedGitHub;
import ru.tinkoff.edu.java.linkparser.records.ParsedStackOverflow;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;
import ru.tinkoff.edu.java.scrapper.domain.models.Track;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateResponse;
import ru.tinkoff.edu.java.scrapper.service.interfaces.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.interfaces.MessageSender;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientGitHub;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientStackOverflow;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainLinkUpdater implements LinkUpdater {

    private final MessageSender sender;
    private final WebClientGitHub gitHubClient;
    private final WebClientStackOverflow stackOverflowClient;
    private final LinkRepository linkRepository;
    private final TrackRepository trackRepository;

    @Override
    public void update() {
        List<Link> linksToUpdate = linkRepository.findAllToUpdate();
        for (Link link : linksToUpdate) {
            log.info("Founded link to update: " + link.getPath());
            link.setCheckedAt(OffsetDateTime.now());
            linkRepository.update(link);
            var record = ParserHandler.parse(URI.create(link.getPath()));
            if (record instanceof ParsedGitHub) {
                GitHubResponse ghResponse = gitHubClient.fetchGitHubRepository(
                        ((ParsedGitHub) record).user(),
                        ((ParsedGitHub) record).repository());
                if (ghResponse.issuesCount() > link.getActionCount()) {
                    var issuesDiff = ghResponse.issuesCount() - link.getActionCount();
                    link.setActionCount(ghResponse.issuesCount());
                    link.setLastActivity(ghResponse.updatedAt());
                    log.info("Link update successful, sending changes to bot...");
                    sender.send(new LinkUpdateResponse(
                            link.getId(),
                            URI.create(link.getPath()),
                            "New issues: " + issuesDiff,
                            getUsers(link)));
                } else if (ghResponse.issuesCount() < link.getActionCount()) {
                    var issuesDiff = link.getActionCount() - ghResponse.issuesCount();
                    link.setActionCount(ghResponse.issuesCount());
                    link.setLastActivity(ghResponse.updatedAt());
                    log.info("Link update successful, sending changes to bot...");
                    sender.send(new LinkUpdateResponse(
                            link.getId(),
                            URI.create(link.getPath()),
                            "Closed issues: " + issuesDiff,
                            getUsers(link)));
                } else if (link.getLastActivity().isBefore(ghResponse.updatedAt())) {
                    link.setLastActivity(ghResponse.updatedAt());
                    log.info("Link update successful, sending changes to bot...");
                    sender.send(new LinkUpdateResponse(
                            link.getId(),
                            URI.create(link.getPath()),
                            "The repository has been updated!",
                            getUsers(link)));
                }
            }
            if (record instanceof ParsedStackOverflow) {
                StackOverflowResponse soResponse = stackOverflowClient.fetchStackOverflowQuestion(
                        ((ParsedStackOverflow) record).id());
                if (soResponse.answersCount() > link.getActionCount()) {
                    var answersDiff = soResponse.answersCount() - link.getActionCount();
                    link.setActionCount(soResponse.answersCount());
                    link.setLastActivity(soResponse.lastActivity());
                    log.info("Link update successful, sending changes to bot...");
                    sender.send(new LinkUpdateResponse(
                            link.getId(),
                            URI.create(link.getPath()),
                            "New answers: " + answersDiff,
                            getUsers(link)));
                } else if (soResponse.answersCount() < link.getActionCount()) {
                    var answersDiff = link.getActionCount() - soResponse.answersCount();
                    link.setActionCount(soResponse.answersCount());
                    link.setLastActivity(soResponse.lastActivity());
                    log.info("Link update successful, sending changes to bot...");
                    sender.send(new LinkUpdateResponse(
                            link.getId(),
                            URI.create(link.getPath()),
                            "Deleted answers: " + answersDiff,
                            getUsers(link)));
                } else if (link.getLastActivity().isBefore(soResponse.lastActivity())) {
                    link.setLastActivity(soResponse.lastActivity());
                    log.info("Link update successful, sending changes to bot...");
                    sender.send(new LinkUpdateResponse(
                            link.getId(),
                            URI.create(link.getPath()),
                            "The question has been updated!",
                            getUsers(link)));
                }
            }
            linkRepository.update(link);
        }
    }

    private @NotNull ArrayList<Long> getUsers(Link link) {
        List<Track> allTracks = trackRepository.findAllTracksWithLink(link);
        ArrayList<Long> users = new ArrayList<>();
        for(Track track : allTracks) {
            users.add(track.getChatId());
        }
        return users;
    }
}
