package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkparser.parsers.ParserHandler;
import ru.tinkoff.edu.java.linkparser.records.ParsedGitHub;
import ru.tinkoff.edu.java.linkparser.records.ParsedStackOverflow;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.domain.models.Link;
import ru.tinkoff.edu.java.scrapper.domain.models.TgChat;
import ru.tinkoff.edu.java.scrapper.domain.models.Track;
import ru.tinkoff.edu.java.scrapper.exceptions.AddedLinkExistsException;
import ru.tinkoff.edu.java.scrapper.exceptions.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exceptions.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientGitHub;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientStackOverflow;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService implements ru.tinkoff.edu.java.scrapper.service.interfaces.LinkService {

    private final LinkRepository linkRepository;
    private final TgChatRepository tgChatRepository;
    private final TrackRepository trackRepository;
    private final WebClientGitHub gitHubClient;
    private final WebClientStackOverflow stackOverflowClient;

    @Override
    public Link add(long tgChatId, @NotNull URI url) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);

        if(!tgChatRepository.isExists(chat)) throw new ChatNotFoundException("Chat not found.");
        var record = ParserHandler.parse(url);
        if(record == null) throw new LinkNotFoundException("This link type is not supported.");

        Link link = new Link();
        link.setPath(url.toString());

        if(record instanceof ParsedGitHub) {
            if(linkRepository.isExists(link)) {
                link = linkRepository.findByUrl(link);
                if(trackRepository.isTracked(chat, link)) throw new AddedLinkExistsException("Link already added.");
            } else {
                GitHubResponse response = gitHubClient.fetchGitHubRepository(
                        ((ParsedGitHub) record).user(),
                        ((ParsedGitHub) record).repository());
                link.setLastActivity(response.updatedAt());
                link.setActionCount(response.issuesCount());
                link = linkRepository.add(link);
            }
        }

        if(record instanceof ParsedStackOverflow){
            if(linkRepository.isExists(link)) {
                link = linkRepository.findByUrl(link);
                if(trackRepository.isTracked(chat, link)) throw new AddedLinkExistsException("Link already added.");
            } else {
                StackOverflowResponse response = stackOverflowClient.fetchStackOverflowQuestion(
                        ((ParsedStackOverflow) record).id());
                link.setLastActivity(response.lastActivity());
                link.setActionCount(response.answersCount());
                link = linkRepository.add(link);
            }
        }

        Track track = new Track();
        track.setChatId(tgChatId);
        track.setLinkId(link.getId());
        trackRepository.add(track);
        return link;
    }

    @Override
    public Link remove(long tgChatId, @NotNull URI url) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        if(!tgChatRepository.isExists(chat)) throw new ChatNotFoundException("Chat not found.");
        var record = ParserHandler.parse(url);
        if(record == null) throw new LinkNotFoundException("Link not found.");
        Link link = new Link();
        link.setPath(url.toString());

        if(!linkRepository.isExists(link)) throw new LinkNotFoundException("Link not found.");
        link = linkRepository.findByUrl(link);
        Track track = new Track();
        track.setChatId(tgChatId);
        track.setLinkId(link.getId());
        if(trackRepository.remove(track) == 0) throw new LinkNotFoundException("You are not following this link.");
        if(!trackRepository.isTrackedByAnyone(link)) linkRepository.remove(link);
        return link;
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        List<Link> links = new ArrayList<>();

        if(!tgChatRepository.isExists(chat)) throw new ChatNotFoundException("Chat not found.");
        List<Track> allTracks = trackRepository.findAllTracksByUser(chat);
        for(Track current : allTracks) {
            Link temp = new Link();
            temp.setId(current.getLinkId());
            links.add(linkRepository.findById(temp));
        }
        return links;
    }
}
