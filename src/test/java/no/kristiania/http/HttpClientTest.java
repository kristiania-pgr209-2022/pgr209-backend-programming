package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {

    @Test
    void shouldReadStatusCode() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals(200, client.getStatusCode());
    }

    @Test
    void shouldReadNotFoundStatusCode() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/bullshit");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReadHttpResponseHeader() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
        assertEquals("text/html; charset=utf-8", client.getHeader("CONTENT-TYPE"));
        assertEquals("close", client.getHeader("Connection"));
    }

    @Test
    void shouldReadContentLength() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals("3741", client.getHeader("Content-length"));
        assertEquals(3741, client.getContentLength());
    }

    @Test
    void shouldReadResponseBody() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        String body = client.getBody();
        assertTrue(body.startsWith("<!DOCTYPE html>"));
        assertTrue(body.endsWith("</body>\n</html>"));
    }






}
