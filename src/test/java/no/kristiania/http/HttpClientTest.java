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

}
