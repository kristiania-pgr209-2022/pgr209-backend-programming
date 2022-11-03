package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.eclipse.jetty.plus.jndi.Resource;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.naming.NamingException;
import java.util.Map;

class MyResourceConfig extends ResourceConfig {

    private final ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<>();

    private final EntityManagerFactory entityManagerFactory;

    public MyResourceConfig() throws NamingException {
        super(BooksEndpoint.class);
        setProperties(Map.of("jersey.config.server.wadl.disableWadl", "true"));
        new Resource("jdbc/dataSource", Database.createDataSource());
        entityManagerFactory = Persistence.createEntityManagerFactory("library");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BookDao.class).to(BookDao.class);
                bindFactory(threadEntityManager::get)
                        .to(EntityManager.class);
            }
        });
    }

    public EntityManager createEntityManager() {
        var entityManager = entityManagerFactory.createEntityManager();
        threadEntityManager.set(entityManager);
        return entityManager;
    }

    public void removeEntityManager() {
        threadEntityManager.remove();
    }
}
