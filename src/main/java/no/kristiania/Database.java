package no.kristiania;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Database {
    public static DataSource createDataSource() throws IOException {
        var props = new Properties();
        try (var fileInputStream = new FileInputStream("application.properties")) {
            props.load(fileInputStream);
        }
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(props.getProperty("dataSource.url"));
        dataSource.setUsername(props.getProperty("dataSource.username"));
        dataSource.setPassword(props.getProperty("dataSource.password"));
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return dataSource;
    }
}
