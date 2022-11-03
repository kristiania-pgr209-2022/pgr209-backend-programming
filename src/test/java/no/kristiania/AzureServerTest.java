package no.kristiania;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureServerTest {

    private AzureServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new AzureServer(0);
        server.start();
    }

    @Test
    void shouldServeFrontPage() throws Exception {
        var connection = openConnection();
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);
    }

    private HttpURLConnection openConnection() throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), "/").openConnection();
    }
}
