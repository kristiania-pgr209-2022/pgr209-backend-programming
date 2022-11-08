package no.kristiania;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class AzureServerTest {

    private AzureServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new AzureServer(0, InMemoryDatabase.createDataSource());
        server.start();
    }

    @Test
    void shouldServeFrontPage() throws Exception {
        var connection = openConnection("/");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);
    }

    @Test
    void shouldGetSavedBooks() throws IOException {
        var postConnection = openConnection("/api/books");
        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.getOutputStream().write("""
                {
                    "title": "Java in a Nutshell",
                    "authorName": "David Flanagan"
                }
                """.getBytes(UTF_8));
        assertThat(postConnection.getResponseCode())
                .as(postConnection.getResponseMessage())
                .isEqualTo(204);

        var connection = openConnection("/api/books");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(UTF_8)
                .contains("David Flanagan");
    }


    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }
}
