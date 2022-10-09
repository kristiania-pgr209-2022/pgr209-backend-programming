package no.kristiania.library;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
class LibraryServerTest {

    @Test
    void shouldServeFrontPage() throws Exception {
        var server = new LibraryServer(0);
        server.start();
        URL frontPage = server.getURL();
        HttpURLConnection connection = (HttpURLConnection) frontPage.openConnection();
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream()).asString(StandardCharsets.UTF_8)
                .contains("<title>Kristiania Library</title>");
    }
}
