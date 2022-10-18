package no.kristiania.library;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

class LibraryServerTest {

    @Test
    void shouldGetPostedResources() throws Exception {
        var server = new LibraryServer(0, createTestDataSource());
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

    private DataSource createTestDataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
