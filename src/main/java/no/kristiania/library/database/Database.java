package no.kristiania.library.database;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Database {
    public static HikariDataSource getDataSource() throws IOException {
        var properties = new Properties();
        try (var reader = new FileReader("application.properties")) {
            properties.load(reader);
        }
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getProperty("jdbc.url"));
        dataSource.setUsername(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
