package no.kristiania.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {

    @Test
    void shouldReadStatusCode() {
        var client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals(200, client.getStatusCode());
    }

}
