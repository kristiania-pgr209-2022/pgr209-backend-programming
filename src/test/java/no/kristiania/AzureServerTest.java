package no.kristiania;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class AzureServerTest {

    private AzureServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new AzureServer(0);
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
    void shouldGetPostedBook() throws IOException {
        var postConnection = openConnection("/api/books");
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        postConnection.getOutputStream().write("""
            {
                "authorName": "David Flanagan",
                "title": "Java in a Nutshell"
            }
            """.getBytes(UTF_8));
        assertThat(postConnection.getResponseCode()).isEqualTo(204);

        var getConnection = openConnection("/api/books");
        assertThat(getConnection.getResponseCode()).isEqualTo(200);
        assertThat(getConnection.getInputStream()).asString(UTF_8)
                .contains("David Flanagan")
                .contains("Java in a Nutshell");
    }



    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }
}
