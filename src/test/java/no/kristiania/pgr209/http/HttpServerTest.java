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
        var client = new HttpClient("localhost", httpServer.getPort(), "/nothing/here");
        assertEquals(404, client.getStatus());
        assertEquals("File not found /nothing/here", client.getBody());
    }

    @Test
    void shouldServePageFromDisk() throws IOException {
        String fileContent = "A file create at " + LocalDateTime.now();
        Files.writeString(root.resolve("example-file.txt"), fileContent);

        var client = new HttpClient("localhost", httpServer.getPort(), "/example-file.txt");
        assertEquals(200, client.getStatus());
        assertEquals(fileContent, client.getBody());
    }

    @Test
    void shouldServeMultiplePages() throws IOException {
        String fileContent = "A file create at " + LocalDateTime.now();
        Files.writeString(root.resolve("other-file.txt"), fileContent);

        assertEquals(200,
                new HttpClient("localhost", httpServer.getPort(), "/other-file.txt").getStatus()
        );
        assertEquals(200,
                new HttpClient("localhost", httpServer.getPort(), "/other-file.txt").getStatus()
        );
    }

    @Test
    void shouldServeIndexFile() throws IOException {
        String fileContent = "A file create at " + LocalDateTime.now();
        Files.writeString(root.resolve("index.html"), fileContent);

        var client = new HttpClient("localhost", httpServer.getPort(), "/");
        assertEquals(fileContent, client.getBody());
    }
}
