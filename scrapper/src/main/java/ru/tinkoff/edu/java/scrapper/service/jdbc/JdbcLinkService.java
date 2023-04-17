package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkparser.parsers.ParserHandler;
import ru.tinkoff.edu.java.linkparser.records.ParsedGitHub;
import ru.tinkoff.edu.java.linkparser.records.ParsedStackOverflow;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.interfaces.TrackRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.AddedLinkExistsException;
import ru.tinkoff.edu.java.scrapper.exceptions.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.exceptions.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.models.Link;
import ru.tinkoff.edu.java.scrapper.models.TgChat;
import ru.tinkoff.edu.java.scrapper.models.Track;
import ru.tinkoff.edu.java.scrapper.service.interfaces.LinkService;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.dto.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientGitHub;
import ru.tinkoff.edu.java.scrapper.web.clients.interfaces.WebClientStackOverflow;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final TgChatRepository tgChatRepository;
    private final TrackRepository trackRepository;
    private final WebClientGitHub gitHubClient;
    private final WebClientStackOverflow stackOverflowClient;

    @Autowired
    public JdbcLinkService(LinkRepository linkRepository, TgChatRepository tgChatRepository,
                           TrackRepository trackRepository, WebClientGitHub gitHubClient,
                           WebClientStackOverflow stackOverflowClient) {
        this.linkRepository = linkRepository;
        this.tgChatRepository = tgChatRepository;
        this.trackRepository = trackRepository;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    public Link add(long tgChatId, @NotNull URI url) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);

        if(!tgChatRepository.isExists(chat)) throw new ChatNotFoundException("Chat not found.");
        var record = ParserHandler.parse(url);
        if(record == null) throw new LinkNotFoundException("Link is not available now.");

        Link link = new Link();
        link.setDomain(url.getHost());

        if(record instanceof ParsedGitHub) {
            link.setPath("/" + ((ParsedGitHub) record).user() + "/" + ((ParsedGitHub) record).repository());
            if(linkRepository.isExists(link)) {
                link = linkRepository.findByUrl(link);
                if(trackRepository.isTracked(chat, link)) throw new AddedLinkExistsException("Link already added.");
            } else {
                GitHubResponse response = gitHubClient.fetchGitHubRepository(
                        ((ParsedGitHub) record).user(),
                        ((ParsedGitHub) record).repository());
                link.setLastActivity(response.updatedAt());
                link = linkRepository.add(link);
            }
        }

        if(record instanceof ParsedStackOverflow){
            link.setPath("/questions/" + ((ParsedStackOverflow) record).id());
            if(linkRepository.isExists(link)) {
                link = linkRepository.findByUrl(link);
                if(trackRepository.isTracked(chat, link)) throw new AddedLinkExistsException("Link already added.");
            } else {
                StackOverflowResponse response = stackOverflowClient.fetchStackOverflowQuestion(
                        ((ParsedStackOverflow) record).id());
                link.setLastActivity(response.lastActivity());
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
    public void remove(long tgChatId, @NotNull URI url) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);

        if(!tgChatRepository.isExists(chat)) throw new ChatNotFoundException("Chat not found.");
        var record = ParserHandler.parse(url);
        if(record == null) throw new LinkNotFoundException("Link not found.");

        Link link = new Link();
        link.setDomain(url.getHost());
        if(record instanceof ParsedGitHub)
            link.setPath("/" + ((ParsedGitHub) record).user() + "/" + ((ParsedGitHub) record).repository());
        if(record instanceof ParsedStackOverflow)
            link.setPath("/questions/" + ((ParsedStackOverflow) record).id());

        if(!linkRepository.isExists(link)) throw new LinkNotFoundException("Link not found.");
        link = linkRepository.findByUrl(link);
        Track track = new Track();
        track.setChatId(tgChatId);
        track.setLinkId(link.getId());
        if(trackRepository.remove(track) == 0) throw new LinkNotFoundException("Link not found.");
        if(!trackRepository.isTrackedByAnyone(link)) linkRepository.remove(link);
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        List<Link> links = new ArrayList<>();

        if(!tgChatRepository.isExists(chat)) throw new ChatNotFoundException("Chat not found.");
        List<Track> allTracks = trackRepository.findAllById(chat);
        for(Track current : allTracks) {
            Link temp = new Link();
            temp.setId(current.getLinkId());
            links.add(linkRepository.findById(temp));
        }
        return links;
    }
}
