package no.kristiania.library;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTest {

    @Test
    void shouldPostAndGetBooks() {
        var server = new LibraryServer(0);
        server.start();

        var postConnection = (HttpURLConnection) new URL(server.getURL(), "/api/books").openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        postConnection.getOutputStream().write("""
                {
                    "title":"Java in a Nutshell",
                    "author":"David Flanagan"
                }
                """.getBytes(StandardCharsets.UTF_8));
        assertThat(postConnection.getResponseCode()).isEqualTo(204);

        var connection = (HttpURLConnection) new URL(server.getURL(), "/api/books").openConnection();
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("Java in a Nutshell");
    }
}
