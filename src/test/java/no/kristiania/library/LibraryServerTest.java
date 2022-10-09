package no.kristiania.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
class LibraryServerTest {

    private LibraryServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new LibraryServer(0);
        server.start();
    }

    @Test
    void shouldServeFrontPage() throws Exception {
        var connection = openConnection("/");
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream()).asString(StandardCharsets.UTF_8)
                .contains("<title>Kristiania Library</title>");
    }

    @Test
    void shouldListBooksAsJson() throws IOException {
        var connection = openConnection("/api/books");
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream()).asString(StandardCharsets.UTF_8)
                .contains("[{\"title\":\"Hello World\"");
    }

    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }
}
