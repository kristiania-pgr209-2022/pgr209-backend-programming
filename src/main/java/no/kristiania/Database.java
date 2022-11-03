package no.kristiania;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class Database {
    public static DataSource createDataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return dataSource;
    }
}
