package parsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.linkparser.parsers.ParserHandler;
import ru.tinkoff.edu.java.linkparser.records.ParsedGitHub;
import ru.tinkoff.edu.java.linkparser.records.ParsedStackOverflow;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkParserTest {
    private final String GOOD_GITHUB_URL = "https://github.com/username/repository";
    private final String SHORT_GITHUB_URL = "https://github.com";
    private final String ANOTHER_HOST_GITHUB_URL = "https://vk.com/username/repository";
    private final String GOOD_STACKOVERFLOW_URL = "https://stackoverflow.com/questions/75933417/tooltip-control";
    private final String BAD_STACKOVERFLOW_URL = "https://stackoverflow.com/users/100297/martijn-pieters";
    private final String NO_QUESTIONS_ID_STACKOVERFLOW_URL = "https://stackoverflow.com/questions/tagged/java";
    private final String SHORT_STACKOVERFLOW_URL = "https://stackoverflow.com";
    private final String ANOTHER_HOST_STACKOVERFLOW_URL = "https://google.com/questions/75933232/put-json-into-haspmap";

    @Test
    @DisplayName("Good GutHub url case")
    void check_GOOD_GITHUB_URL() {
        URI uri = URI.create(GOOD_GITHUB_URL);
        assertThat(ParserHandler.parse(uri)).isEqualTo(new ParsedGitHub("username", "repository"));
    }

    @Test
    @DisplayName("Bad GitHub url case with short link")
    void check_SHORT_GITHUB_URL() {
        URI uri = URI.create(SHORT_GITHUB_URL);
        assertThat(ParserHandler.parse(uri)).isEqualTo(null);
    }

    @Test
    @DisplayName("Bad GitHub url case with another hostname")
    void check_ANOTHER_HOST_GITHUB_URL() {
        URI uri = URI.create(ANOTHER_HOST_GITHUB_URL);
        assertThat(ParserHandler.parse(uri)).isEqualTo(null);
    }

    @Test
    @DisplayName("Good StackOverflow url case")
    void check_GOOD_STACKOVERFLOW_URL() {
        URI uri = URI.create(GOOD_STACKOVERFLOW_URL);
        assertThat(ParserHandler.parse(uri)).isEqualTo(new ParsedStackOverflow("75933417"));
    }

    @Test
    @DisplayName("Bad StackOverflow url case with valid hostname")
    void check_BAD_STACKOVERFLOW_URL() {
        URI uri = URI.create(BAD_STACKOVERFLOW_URL);
        assertThat(ParserHandler.parse(uri)).isEqualTo(null);
    }

    @Test
    @DisplayName("Bad StackOverflow url case with no question id")
    void check_NO_QUESTIONS_ID_STACKOVERFLOW_URL() {
        URI uri = URI.create(NO_QUESTIONS_ID_STACKOVERFLOW_URL);
        assertThat(ParserHandler.parse(uri)).isEqualTo(null);
    }

    @Test
    @DisplayName("Bad StackOverflow url case with short link")
    void check_SHORT_STACKOVERFLOW_URL() {
        URI uri = URI.create(SHORT_STACKOVERFLOW_URL);
        assertThat(ParserHandler.parse(uri)).isEqualTo(null);
    }

    @Test
    @DisplayName("Bad StackOverflow url case with another hostname")
    void check_ANOTHER_HOST_STACKOVERFLOW_URL() {
        URI uri = URI.create(ANOTHER_HOST_STACKOVERFLOW_URL);
        assertThat(ParserHandler.parse(uri)).isEqualTo(null);
    }
}
