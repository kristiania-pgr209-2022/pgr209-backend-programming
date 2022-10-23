package no.kristiania.library;

import jakarta.persistence.EntityManager;
import no.kristiania.library.database.BookDao;
import no.kristiania.library.database.jpa.JpaBookDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

class LibraryResourceConfig extends ResourceConfig {
    private static final ThreadLocal<EntityManager> entityManager = new ThreadLocal<>();

    public static void setEntityManager(EntityManager entityManager) {
        LibraryResourceConfig.entityManager.set(entityManager);
    }

    public LibraryResourceConfig() {
        super(BookResource.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JpaBookDao.class).to(BookDao.class);
                bindFactory(entityManager::get)
                        .to(EntityManager.class)
                        .in(RequestScoped.class);
            }
        });
    }
}
