package no.kristiania.library;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

class LibraryServerTest {

    @Test
    void shouldGetPostedResources() throws Exception {
        var server = new LibraryServer(0);
        server.start();
        var url = new URL(server.getURL(), "/api/books");

        var postConnection = (HttpURLConnection)url.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        //language=JSON
        postConnection.getOutputStream().write("""
        {"name":"test"}
        """.getBytes());
        assertThat(postConnection.getResponseCode()).as(postConnection.getRequestMethod())
                .isEqualTo(204);

        var getConnection = (HttpURLConnection)url.openConnection();
        assertThat(getConnection.getResponseCode()).as(getConnection.getRequestMethod())
                .isEqualTo(200);
        assertThat(getConnection.getInputStream()).asString(UTF_8).contains("""
                        {"name":"test"}""");
    }
}
