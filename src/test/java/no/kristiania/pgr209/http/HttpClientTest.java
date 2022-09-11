package no.kristiania.pgr209.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {

    @Test
    void shouldGetSuccessfulHttpResponse() {
        var client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals(200, client.getStatus());
    }

    @Test
    void shouldGetFailureHttpResponse() {
        var client = new HttpClient("httpbin.org", 80, "/status/403");
        assertEquals(403, client.getStatus());
    }

}
