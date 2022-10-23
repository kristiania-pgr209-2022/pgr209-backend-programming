package no.kristiania.library.database;

import com.zaxxer.hikari.HikariDataSource;
import no.kristiania.library.database.jdbc.JdbcBookDao;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Demo {

    private final BookDao bookDao;
    private final LibraryDao libraryDao;
    private final PhysicalBookDao physicalBookDao;

    public Demo(DataSource dataSource) {
        this.bookDao = new JdbcBookDao(dataSource);
        this.libraryDao = new JdbcLibraryDao(dataSource);
        physicalBookDao = new PhysicalBookDao(dataSource);
    }

    public static void main(String[] args) throws SQLException, IOException {
        var properties = new Properties();
        try (var reader = new FileReader("application.properties")) {
            properties.load(reader);
        }

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getProperty("jdbc.url", "jdbc:postgresql://localhost:5432/library"));
        dataSource.setUsername(properties.getProperty("jdbc.username", "kristiania_app"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        new Demo(dataSource).run();
    }

    private void run() throws SQLException {
        var book = SampleData.sampleBook();
        bookDao.save(book);
        var library = SampleData.sampleLibrary();
        libraryDao.save(library);

        physicalBookDao.insert(library, book);
    }

}
