package no.kristiania.pgr209.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    private final HttpServer httpServer = new HttpServer(0);
    private final Path root = Path.of("target/test-classes/" + UUID.randomUUID());

    public HttpServerTest() throws IOException {
        httpServer.start();
        Files.createDirectories(root);
        httpServer.setRoot(root);
    }

    @Test
    void shouldShowNotFoundPage() throws IOException {
        var client = makeRequest("/nothing/here");
        assertEquals(404, client.getStatus());
        assertEquals("File not found /nothing/here", client.getBody());
    }

    @Test
    void shouldServePageFromDisk() throws IOException {
        String fileContent = "A file create at " + LocalDateTime.now();
        Files.writeString(root.resolve("example-file.txt"), fileContent);

        var client = makeRequest("/example-file.txt");
        assertEquals(200, client.getStatus());
        assertEquals(fileContent, client.getBody());
    }

    @Test
    void shouldServeMultiplePages() throws IOException {
        String fileContent = "A file create at " + LocalDateTime.now();
        Files.writeString(root.resolve("other-file.txt"), fileContent);

        assertEquals(200,
                makeRequest("/other-file.txt").getStatus()
        );
        assertEquals(200,
                makeRequest("/other-file.txt").getStatus()
        );
    }

    @Test
    void shouldServeIndexFile() throws IOException {
        String fileContent = "A file create at " + LocalDateTime.now();
        Files.writeString(root.resolve("index.html"), fileContent);

        var client = makeRequest("/");
        assertEquals(fileContent, client.getBody());
    }

    @Test
    void shouldReturnContentType() throws IOException {
        Files.writeString(root.resolve("index.html"), "<h1>Hello world</h1>");
        Files.writeString(root.resolve("plain.txt"), "Hello world");
        Files.writeString(root.resolve("style.css"), "body { background: red }");

        assertEquals(
                "text/html",
                makeRequest("/").getHeader("Content-Type")
        );
        assertEquals(
                "text/plain",
                makeRequest("/plain.txt").getHeader("Content-Type")
        );
        assertEquals(
                "text/css",
                makeRequest("/style.css").getHeader("Content-Type")
        );
    }

    @Test
    void shouldReturn500ForIllegalCharactersInFilename() throws IOException {
        assertEquals(500, makeRequest("hello*world").getStatus());
    }

    @Test
    void shouldProcessEchoRequest() throws IOException {
        var client = makeRequest("/echo?input-string=hello");
        assertEquals(200, client.getStatus());
        assertEquals("hello", client.getBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
        assertEquals("hellothere", makeRequest("/echo?input-string=hellothere").getBody());
    }

    @Test
    void shouldEchoContentType() throws IOException {
        var client = makeRequest("/echo?content-type=text/html&input-string=<h1>hello</h1>");
        assertEquals(200, client.getStatus());
        assertEquals("<h1>hello</h1>", client.getBody());
        assertEquals("text/html", client.getHeader("Content-Type"));
    }

    private HttpClient makeRequest(String requestTarget) throws IOException {
        return new HttpClient("localhost", httpServer.getPort(), requestTarget);
    }
}
