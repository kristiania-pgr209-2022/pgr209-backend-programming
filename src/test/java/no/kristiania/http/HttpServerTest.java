package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    private Path serverRoot = Path.of("target", "test-files");

    @Test
    void shouldRespondWith404ToUnknownUrl() throws IOException {
        var server = new HttpServer(0, Path.of("."));
        var client = new HttpRequestResult("localhost", server.getPort(), "/unknown-url");
        assertEquals(404, client.getStatusCode());
        assertEquals("Unknown URL '/unknown-url'", client.getBody());
    }

    @Test
    void shouldRespondWith200ForKnownUrl() throws IOException {
        Files.createDirectories(serverRoot);
        Path file = serverRoot.resolve("example-file.txt");
        var content = "Hello There " + LocalDateTime.now();
        Files.writeString(file, content);
        var server = new HttpServer(0, serverRoot);
        var client = new HttpRequestResult("localhost", server.getPort(), "/" + file.getFileName());
        assertEquals(200, client.getStatusCode());
        assertEquals(content, client.getBody());
    }

    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        Files.createDirectories(serverRoot);
        Path file = serverRoot.resolve("index.html");
        Files.writeString(file, "<h1>Hello world</h1>");

        var server = new HttpServer(0, serverRoot);
        assertEquals(200,
                new HttpRequestResult("localhost", server.getPort(), "/" + file.getFileName())
                        .getStatusCode()
        );
        assertEquals(200,
                new HttpRequestResult("localhost", server.getPort(), "/" + file.getFileName())
                        .getStatusCode()
        );
    }

    @Test
    void shouldReturn500OnUnhandledErrors() throws IOException {
        var server = new HttpServer(0, serverRoot);
        assertEquals(500,
                new HttpRequestResult("localhost", server.getPort(), "/foo*bar")
                        .getStatusCode()
        );
    }

    @Test
    void shouldHandleEchoRequest() throws IOException {
        var server = new HttpServer(0, serverRoot);
        assertEquals("Hello",
                new HttpRequestResult("localhost", server.getPort(), "/api/echo?input-string=Hello")
                        .getBody()
        );
    }
}
