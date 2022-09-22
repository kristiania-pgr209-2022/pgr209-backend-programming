package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void shouldRespondWith404ToUnknownUrl() throws IOException {
        var server = new HttpServer(0);
        var client = new HttpRequestResult("localhost", server.getPort(), "/unknown-url");
        assertEquals(404, client.getStatusCode());
        assertEquals("Unknown URL '/unknown-url'", client.getBody());
    }

    @Test
    void shouldRespondWith200ForKnownUrl() throws IOException {
        Path file = Path.of("example-file.txt");
        Files.writeString(file, "Hello There " + LocalDateTime.now());
        var server = new HttpServer(0);
        var client = new HttpRequestResult("localhost", server.getPort(), "/" + file.getFileName());
        assertEquals(200, client.getStatusCode());
    }


}
