package no.kristiania.library;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTest {

    @Test
    void shouldServeHomePage() throws Exception {
        var server = new LibraryServer(0);
        server.start();
        var connection = (HttpURLConnection) server.getURL().openConnection();

        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<h1>Kristiania Library</h1>");
    }

}
