package no.kristiania.pgr209.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    @Test
    void shouldShowNotFoundPage() throws IOException {
        var httpServer = new HttpServer(0);
        httpServer.start();
        var client = new HttpClient("localhost", httpServer.getPort(), "/nothing/here");
        assertEquals(404, client.getStatus());
        assertEquals("File not found /nothing/here", client.getBody());
    }

}
