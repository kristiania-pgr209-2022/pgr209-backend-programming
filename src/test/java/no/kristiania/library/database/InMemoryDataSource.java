package no.kristiania.library.database;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class InMemoryDataSource {
    public static DataSource createTestDataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:testDatabase;DB_CLOSE_DELAY=-1");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return dataSource;
    }
}
