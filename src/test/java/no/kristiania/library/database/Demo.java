package no.kristiania.library.database;

import no.kristiania.library.database.jdbc.JdbcBookDao;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;
import no.kristiania.library.database.jdbc.JdbcPhysicalBookDao;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class Demo {

    private final BookDao bookDao;
    private final LibraryDao libraryDao;
    private final PhysicalBookDao physicalBookDao;

    public Demo(DataSource dataSource) {
        this.bookDao = new JdbcBookDao(dataSource);
        this.libraryDao = new JdbcLibraryDao(dataSource);
        physicalBookDao = new JdbcPhysicalBookDao(dataSource);
    }

    public static void main(String[] args) throws SQLException, IOException {
        new Demo(Database.createDataSource()).run();
    }

    private void run() throws SQLException {
        var book = SampleData.sampleBook();
        bookDao.save(book);
        var library = SampleData.sampleLibrary();
        libraryDao.save(library);

        physicalBookDao.insert(library, book);
    }

}
