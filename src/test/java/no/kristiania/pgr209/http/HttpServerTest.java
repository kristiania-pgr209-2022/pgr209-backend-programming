package no.kristiania.pgr209.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

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

    @Test
    void shouldServePageFromDisk() throws IOException {
        var httpServer = new HttpServer(0);
        httpServer.start();
        String fileContent = "A file create at " + LocalDateTime.now();
        Files.writeString(Path.of("target/test-classes/example-file.txt"), fileContent);
        httpServer.setRoot(Path.of("target/test-classes"));

        var client = new HttpClient("localhost", httpServer.getPort(), "/example-file.txt");
        assertEquals(200, client.getStatus());
        assertEquals(fileContent, client.getBody());
    }

}
