package no.kristiania.library;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class LibraryServerTest {

    @Test
    void shouldShowFrontPage() {
        var server = new LibraryServer(0);
        var connection = (HttpURLConnection)server.getURL().openConnection();
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contain("<title>Kristiania Library</title>");
    }
}
