package no.kristiania.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTest {

    private LibraryServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new LibraryServer(0);
        server.start();
    }

    @Test
    void shouldServeHomePage() throws IOException {
        var connection = openConnection("/");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Kristiania Library</title>");
    }


    @Test
    void shouldListBooks() throws IOException {
        var connection = openConnection("/api/books");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"title\":\"Java in a nutshell\"");
    }

    private HttpURLConnection openConnection(String spec) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), spec).openConnection();
    }
}
