package no.kristiania.library;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class LibraryServerTest {

    @Test
    void shouldServeFrontPage() throws IOException {
        var server = new LibraryServer(0);
        server.start();

        var connection = (HttpURLConnection) new URL(server.getURL(), "/").openConnection();
        assertThat(connection.getResponseCode()).as(connection.getRequestMethod())
                .isEqualTo(200);
        assertThat(connection.getInputStream()).asString(UTF_8)
                .contains("<title>Kristiania Library</title>");
    }

}
