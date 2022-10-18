package no.kristiania.library.database;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Demo {

    private final JdbcBookDao bookDao;
    private final JdbcLibraryDao libraryDao;
    private final JdbcPhysicalBookDao physicalBookDao;

    public Demo(DataSource dataSource) {
        this.bookDao = new JdbcBookDao(dataSource);
        this.libraryDao = new JdbcLibraryDao(dataSource);
        physicalBookDao = new JdbcPhysicalBookDao(dataSource);
    }

    public static void main(String[] args) throws SQLException {
        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/library");
        dataSource.setUser("kristiania_app");
        dataSource.setPassword("this is secret, do not check it in!");
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
