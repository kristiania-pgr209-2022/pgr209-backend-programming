package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    private final Path serverRoot = Path.of("target", "test-files");

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
        assertEquals("hallæ world + universe & galaxy",
                new HttpRequestResult("localhost", server.getPort(), "/api/echo?input-string=hallæ+world+%2B+universe+%26+galaxy")
                        .getBody()
        );
    }

    @Test
    void shouldListIndexFileForDirectory() throws IOException {
        Files.createDirectories(serverRoot);
        Path file = serverRoot.resolve("index.html");
        var content = "<h1>Hello There " + LocalDateTime.now() + "</h1>";
        Files.writeString(file, content);
        var server = new HttpServer(0, serverRoot);
        var client = new HttpRequestResult("localhost", server.getPort(), "/");
        assertEquals(content, client.getBody());
    }

    @Test
    void shouldGive404ForMissingIndexFileInDirectory() throws IOException {
        Files.createDirectories(serverRoot.resolve("something"));
        var server = new HttpServer(0, serverRoot);
        var client = new HttpRequestResult("localhost", server.getPort(), "/something/");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldServeBinaryFiles() throws IOException {
        var testResources = Path.of("src", "test", "resources");
        var server = new HttpServer(0, testResources);
        var client = new HttpRequestResult("localhost", server.getPort(), "/image.png");
        assertEquals(
                testResources.resolve("image.png").toFile().length(),
                client.getContentLength()
        );
    }

    @Test
    void shouldSetCookieOnLogin() throws IOException {
        var server = new  HttpServer(80, serverRoot);
        var client = new HttpRequestResult("localhost", server.getPort(), "/api/login?username=adminuser");
        assertEquals(200, client.getStatusCode());
        assertEquals("authenticatedUserName=adminuser; HttpOnly", client.getHeader("Set-Cookie"));

    }
}
