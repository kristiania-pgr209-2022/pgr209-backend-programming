package no.kristiania;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

public class AzureServerTest {

    @Test
    void shouldServerFrontPage() {
        var server = new AzureServer(8080);
        server.start();

        var connection = (HttpURLConnection)server.getURL().openConnection();
        assertThat(connection.getResponseCode()).isEqualTo(200);
    }
}
