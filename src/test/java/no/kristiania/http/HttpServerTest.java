package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void shouldRespondWith404ToUnknownUrl() throws IOException {
        var server = new HttpServer(0);
        var client = new HttpRequestResult("localhost", server.getPort(), "/unknown-url");
        assertEquals(404, client.getStatusCode());
    }
}
