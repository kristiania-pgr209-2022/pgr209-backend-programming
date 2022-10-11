package no.kristiania.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.json.Json;
import jakarta.json.JsonObject;
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
    void shouldShowFrontPage() throws Exception {
        var connection = openConnection("/");
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Kristiania Library</title>");
    }

    @Test
    void shouldShowDynamicContent() throws IOException {
        var connection = openConnection("/api/books");
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getHeaderField("Content-Type"))
                .isEqualTo("application/json");
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"title\":\"Hello World\"");
    }

    @Test
    void shouldAddBook() throws IOException {
        var postConnection = openConnection("/api/books");
        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        JsonObject bookJson = Json.createObjectBuilder()
                .add("title", "Test Book")
                .build();
        postConnection.getOutputStream().write(bookJson.toString().getBytes(StandardCharsets.UTF_8));
        assertThat(postConnection.getResponseCode()).isEqualTo(200);

        var getConnection = openConnection("/api/books");
        assertThat(getConnection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"title\":\"Test Book\"");
    }

    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }
}
