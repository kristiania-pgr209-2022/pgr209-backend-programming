package no.kristiania.library;

import no.kristiania.library.database.Database;
import no.kristiania.library.database.InMemoryDataSource;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTest {

    @Test
    void shouldPostAndGetBooks() throws Exception {
        var server = new LibraryServer(0, InMemoryDataSource.createTestDataSource());
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
