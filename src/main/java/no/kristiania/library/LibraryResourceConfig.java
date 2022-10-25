package no.kristiania.library;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.kristiania.library.database.BookDao;
import no.kristiania.library.database.JpaBookDao;
import org.eclipse.jetty.plus.jndi.Resource;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

import javax.naming.NamingException;
import javax.sql.DataSource;

class LibraryResourceConfig extends ResourceConfig {

    private EntityManagerFactory library = Persistence.createEntityManagerFactory("library");

    public LibraryResourceConfig(DataSource dataSource) throws NamingException {
        super(BookEndpoint.class);

        new Resource("jdbc/dataSource", dataSource);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JpaBookDao.class).to(BookDao.class);
                bindFactory(() -> library.createEntityManager())
                        .to(EntityManager.class)
                        .in(RequestScoped.class);
            }
        });
    }
}
