package no.kristiania.pgr209.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {

    @Test
    void shouldGetSuccessfulHttpResponse() {
        assertEquals(200, new HttpClient("httpbin.org", 80, "/html").getStatus());
    }

}
