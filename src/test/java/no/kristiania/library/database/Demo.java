package no.kristiania.library.database;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.eclipse.jetty.plus.jndi.Resource;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class Demo {

    private final BookDao bookDao;
    private final LibraryDao libraryDao;
    private final PhysicalBookDao physicalBookDao;
    private final EntityManager entityManager;

    public Demo(EntityManager entityManager) {
        this.bookDao = new JpaBookDao(entityManager);
        this.libraryDao = new JpaLibraryDao(entityManager);
        physicalBookDao = new JpaPhysicalBookDao(entityManager);
        this.entityManager = entityManager;
    }

    public static void main(String[] args) throws SQLException, IOException, NamingException {
        new Resource("jdbc/dataSource", Database.getDataSource());
        var entityManager = Persistence
                .createEntityManagerFactory("library")
                .createEntityManager();
        new Demo(entityManager).run();
    }

    private void run() throws SQLException {
        entityManager.getTransaction().begin();
        var book = SampleData.sampleBook();
        bookDao.save(book);
        var library = SampleData.sampleLibrary();
        libraryDao.save(library);

        physicalBookDao.insert(library, book);

        entityManager.flush();
        entityManager.getTransaction().commit();
    }

}
