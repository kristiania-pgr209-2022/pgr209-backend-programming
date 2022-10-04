package no.kristiania.library;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class LibraryServerTest {

    @Test
    void shouldServeHomePage() throws IOException {
        var server = new LibraryServer(0);
        var connection = (HttpURLConnection) server.getURL().openConnection();

        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<h1>Kristiania Library</h1>");
    }

}
