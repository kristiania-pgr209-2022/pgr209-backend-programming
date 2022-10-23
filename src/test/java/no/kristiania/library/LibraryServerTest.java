package no.kristiania.library;

import no.kristiania.library.database.Database;
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
        server = new LibraryServer(0, Database.createDataSource());
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

    @Test
    void shouldPostNewBook() throws Exception {
        var postConnection = openConnection("/api/books");
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        postConnection.getOutputStream().write("""
                {"title":"Java In A Nutshell","authorName":"My Test Author"}
                """.getBytes(UTF_8));
        assertThat(postConnection.getResponseCode()).as(postConnection.getResponseMessage())
                .isEqualTo(204);

        var connection = openConnection("/api/books");
        assertThat(connection.getResponseCode()).as(connection.getResponseMessage())
                .isEqualTo(200);
        assertThat(connection.getInputStream()).asString(UTF_8)
                .contains("\"title\":\"Java In A Nutshell\"");
    }

    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }

}
