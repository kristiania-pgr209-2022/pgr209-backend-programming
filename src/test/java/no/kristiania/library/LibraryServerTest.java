package no.kristiania.library;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class LibraryServerTest {

    @Test
    void shouldShowFrontPage() throws IOException {
        var server = new LibraryServer(0);
        var connection = (HttpURLConnection)server.getURL().openConnection();
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Kristiania Library</title>");
    }
}
