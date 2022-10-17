package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Demo {

    private final BookDao bookDao;
    private final LibraryDao libraryDao;
    private final BookCopyDao bookCopyDao;

    public Demo(DataSource dataSource) {
        bookDao = new BookDao(dataSource);
        libraryDao = new LibraryDao(dataSource);
        bookCopyDao = new BookCopyDao(dataSource);
    }

    public static void main(String[] args) throws SQLException {
        new Demo(InmemoryDatabase.testDataSource()).run();
    }

    private void run() throws SQLException {
        var firstBook = SampleData.sampleBook();
        var secondBook = SampleData.sampleBook();
        bookDao.save(firstBook);
        bookDao.save(secondBook);

        var  library = SampleData.sampleLibrary();
        var otherLibrary = SampleData.sampleLibrary();
        libraryDao.save(library);
        libraryDao.save(otherLibrary);

        bookCopyDao.insert(library, firstBook);
        bookCopyDao.insert(library, secondBook);
        bookCopyDao.insert(otherLibrary, firstBook);
    }

}
