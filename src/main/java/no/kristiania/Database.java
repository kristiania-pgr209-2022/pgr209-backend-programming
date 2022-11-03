package no.kristiania;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class Database {
    public static DataSource createDataSource() {
        var dataSource = new SQLServerDataSource();
        dataSource.setURL("jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true");
        dataSource.setUser("sa");
        dataSource.setPassword("yourStrong(123)Password");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return dataSource;
    }
}
