package no.kristiania.library;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class LibraryServerTest {

    @Test
    void shouldShowFrontPage() throws Exception {
        LibraryServer server = new LibraryServer(0);
        server.start();

        URL url = server.getURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assertThat(connection.getResponseCode()).as(connection.getResponseMessage())
                .isEqualTo(200);
        assertThat(connection.getInputStream()).asString(StandardCharsets.UTF_8)
                .contains("<h1>Kristiania Library</h1>");
    }
}
