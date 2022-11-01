package no.kristiania;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureServerTest {

    @Test
    void shouldServeFrontPage() throws Exception {
        var server = new AzureServer(0);
        server.start();

        var connection = (HttpURLConnection)server.getURL().openConnection();
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);
    }
}
