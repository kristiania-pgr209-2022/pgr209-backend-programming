package no.kristiania.http;

import org.junit.jupiter.api.Test;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
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
    void shouldRespondWith404ToUnknownUrlOnHttps() throws IOException, GeneralSecurityException {
        var sslContext = SSLContext.getInstance("TLS");
        var keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        var keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(new FileInputStream("servercert.p12"), "abc123".toCharArray());
        keyManagerFactory.init(keyStore, "abc123".toCharArray());
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        var serverSocket = sslContext.getServerSocketFactory().createServerSocket(0);

        var server = new HttpServer(Path.of("."), serverSocket);

        var clientSslContext = SSLContext.getInstance("TLS");
        var trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        clientSslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        var socket = clientSslContext.getSocketFactory().createSocket("localhost", server.getPort());
        var client = new HttpRequestResult("localhost", "/unknown-url", socket);
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
        var server = new  HttpServer(0, serverRoot);
        var client = new HttpRequestResult("localhost", server.getPort(), "/api/login?username=adminuser");
        assertEquals(200, client.getStatusCode());
        assertEquals("authenticatedUserName=adminuser; HttpOnly; Path=/", client.getHeader("Set-Cookie"));
    }

    @Test
    void shouldUseCookieOnUserinfoRequest() throws IOException {
        var server = new  HttpServer(0, serverRoot);
        var socket = new Socket("localhost", server.getPort());

        var requestTarget = "/api/userinfo";
        socket.getOutputStream().write(
                ("GET " + requestTarget + " HTTP/1.1\r\n" +
                 "Connection: close\r\n" +
                 "Cookie: authenticatedUserName=adminuser; otherCookie=value\r\n" +
                 "\r\n").getBytes(StandardCharsets.UTF_8)
        );

        var response = new HttpMessage(socket);
        assertEquals("adminuser", response.body);
    }
}
