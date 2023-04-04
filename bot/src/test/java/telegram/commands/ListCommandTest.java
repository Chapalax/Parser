package telegram.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.telegram.UserMessageHandler;
import ru.tinkoff.edu.java.bot.telegram.commands.ListCommand;
import ru.tinkoff.edu.java.bot.web.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.web.clients.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.web.clients.dto.ListLinksResponse;

import java.net.URI;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ListCommandTest {
    private static final long ID = 123456L;
    private static final String UNKNOWN_COMMAND = "/test";
    private final String UNKNOWN_COMMAND_WARNING = "This command does not exist.\nList of available commands: /help";
    private final String NO_LINKS_WARNING = "You are not tracking any links yet!\n" +
            "To start tracking updates, use the command /track";
    @Mock
    private ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
    @InjectMocks
    private ListCommand listCommand = new ListCommand(scrapperClient);
    @InjectMocks
    private UserMessageHandler handler = new UserMessageHandler(scrapperClient, listCommand);

    private Update getUpdate(String msg) {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        ReflectionTestUtils.setField(chat, "id", ID);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", msg);
        ReflectionTestUtils.setField(update, "message", message);
        return update;
    }

    @Test
    @DisplayName("Checking a warning message in response to an unknown command")
    void set_UnknownCommand() throws IllegalAccessError {
        SendMessage responseListCommand = handler.process(getUpdate(UNKNOWN_COMMAND));
        assertThat(responseListCommand.getParameters().get("chat_id")).isEqualTo(ID);
        assertThat(responseListCommand.getParameters().get("text")).isEqualTo(UNKNOWN_COMMAND_WARNING);
    }

    @Test
    @DisplayName("Checking the warning message when the list of links is empty")
    void get_EmptyList() throws IllegalAccessError {
        ListLinksResponse listLinksResponse = new ListLinksResponse(new ArrayList<>(), 0);

        when(scrapperClient.getAllLinks(ID)).thenReturn(listLinksResponse);

        SendMessage responseListCommand = handler.process(getUpdate("/list"));
        assertThat(responseListCommand.getParameters().get("chat_id")).isEqualTo(ID);
        assertThat(responseListCommand.getParameters().get("text")).isEqualTo(NO_LINKS_WARNING);
    }

    @Test
    @DisplayName("Good response from list command")
    void get_GoodResponse() throws IllegalAccessError {
        LinkResponse firstLink = new LinkResponse(ID,
                URI.create("https://github.com/pengrad/java-telegram-bot-api"));
        LinkResponse secondLink = new LinkResponse(ID,
                URI.create("https://stackoverflow.com/questions/2811141/is-it-bad-practice-to-use-reflection-in-unit-testing"));
        ArrayList<LinkResponse> responseArrayList = new ArrayList<>();
        responseArrayList.add(firstLink);
        responseArrayList.add(secondLink);
        ListLinksResponse linksResponse = new ListLinksResponse(responseArrayList, 2);
        String resultMessage = "The list of links you are tracking:\n" +
                firstLink.url().toString() + "\n" +
                secondLink.url().toString() + "\n";

        when(scrapperClient.getAllLinks(ID)).thenReturn(linksResponse);

        SendMessage responseListCommand = handler.process(getUpdate("/list"));
        assertThat(responseListCommand.getParameters().get("chat_id")).isEqualTo(ID);
        assertThat(responseListCommand.getParameters().get("text")).isEqualTo(resultMessage);
    }
}
