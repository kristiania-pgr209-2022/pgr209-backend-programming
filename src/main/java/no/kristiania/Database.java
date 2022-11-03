package no.kristiania;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class Database {
    public static DataSource createDataSource() {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true");
        dataSource.setUsername("sa");
        dataSource.setPassword("yourStrong123Password");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return dataSource;
    }
}
