package no.kristiania.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTest {

    private LibraryServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new LibraryServer(0);
        server.start();
    }

    @Test
    void shouldServeFrontPage() throws Exception {
        var connection = openConnection("/");
        assertThat(connection.getResponseCode()).as(connection.getResponseMessage())
                .isEqualTo(200);
        assertThat(connection.getInputStream()).asString(UTF_8)
                .contains("<title>Kristiania Library</title>");
    }

    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }

}
