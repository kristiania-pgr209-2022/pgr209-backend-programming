package no.kristiania.library;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import no.kristiania.library.database.BookDao;
import no.kristiania.library.database.jpa.JpaBookDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

class LibraryResourceConfig extends ResourceConfig {
    public LibraryResourceConfig(DataSource dataSource) {
        super(BookResource.class);
        var entityManagerFactory = Persistence.createEntityManagerFactory("library");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JpaBookDao.class).to(BookDao.class);
                bindFactory(entityManagerFactory::createEntityManager)
                        .to(EntityManager.class);
            }
        });
    }
}
