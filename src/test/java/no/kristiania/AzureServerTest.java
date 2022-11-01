package no.kristiania;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureServerTest {

    @Test
    void shouldServeFrontPage() throws IOException {
        var server = new AzureServer(8080);
        server.start();

        var connection = (HttpURLConnection)server.getURL().openConnection();
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);
    }
}
