package no.kristiania.library.database;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Demo {

    private BookDao bookDao;

    public Demo(DataSource dataSource) {
        this.bookDao = new BookDao(dataSource);
    }

    public static void main(String[] args) throws SQLException {
        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/library");
        dataSource.setUser("kristiania_app");
        dataSource.setPassword("this is secret, do not check it in!");
        new Demo(dataSource).run();
    }

    private void run() throws SQLException {
        bookDao.save(SampleData.sampleBook());
    }

}
