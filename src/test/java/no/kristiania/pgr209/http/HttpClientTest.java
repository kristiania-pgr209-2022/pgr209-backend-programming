package no.kristiania.pgr209.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {

    @Test
    void shouldGetSuccessfulHttpResponse() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals(200, client.getStatus());
        assertEquals("OK", client.getReasonPhrase());
    }

    @Test
    void shouldGetFailureHttpResponse() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/status/403");
        assertEquals(403, client.getStatus());
        assertEquals("FORBIDDEN", client.getReasonPhrase());
    }

    @Test
    void shouldGetNotFoundHttpResponse() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/non/existing/url");
        assertEquals(404, client.getStatus());
        assertEquals("NOT FOUND", client.getReasonPhrase());
    }

    @Test
    void shouldReadResponseHeader() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
        assertEquals("keep-alive", client.getHeader("Connection"));
        assertEquals(3741, client.getContentLength());
    }

    @Test
    void shouldReadResponseBody() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        assertTrue(client.getBody().contains("<h1>Herman Melville - Moby-Dick</h1>"));
    }

    @Test
    void shouldReadRedirect() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/redirect-to?url=http%3A%2F%2Fhttpbin.org%2Fjson");
        assertEquals(302, client.getStatus());
        assertEquals("http://httpbin.org/json", client.getHeader("Location"));
    }

}
