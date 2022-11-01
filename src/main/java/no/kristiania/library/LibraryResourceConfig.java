package no.kristiania.library;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import no.kristiania.library.database.BookDao;
import no.kristiania.library.database.jpa.JpaBookDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

class LibraryResourceConfig extends ResourceConfig {

    private ThreadLocal<EntityManager> requestEntityManager = new ThreadLocal<>();

    private EntityManagerFactory entityManagerFactory;

    public LibraryResourceConfig(EntityManagerFactory entityManagerFactory) {
        super(BookEndpoint.class);

        this.entityManagerFactory = entityManagerFactory;
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JpaBookDao.class).to(BookDao.class);
                bindFactory(() -> requestEntityManager.get())
                        .to(EntityManager.class)
                        .in(RequestScoped.class);
            }
        });
    }

    public EntityManager createEntityManagerForRequest() {
        requestEntityManager.set(entityManagerFactory.createEntityManager());
        return requestEntityManager.get();
    }

    public void cleanRequestEntityManager() {
        requestEntityManager.remove();
    }
}
