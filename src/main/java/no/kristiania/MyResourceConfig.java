package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.eclipse.jetty.plus.jndi.Resource;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.naming.NamingException;
import javax.sql.DataSource;

class MyResourceConfig extends ResourceConfig {

    private static EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<>();

    public MyResourceConfig(DataSource dataSource) throws NamingException {
        super(BookEndpoint.class);

        new Resource("jdbc/dataSource", dataSource);
        entityManagerFactory = Persistence.createEntityManagerFactory("library");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BookDao.class).to(BookDao.class);
                bindFactory(() -> threadEntityManager.get())
                        .to(EntityManager.class);
            }
        });
    }

    public static void startTransaction() {
        var entityManager = entityManagerFactory.createEntityManager();
        threadEntityManager.set(entityManager);
        entityManager.getTransaction().begin();
    }

    public static void commitTransaction() {
        threadEntityManager.get().getTransaction().commit();
        threadEntityManager.remove();
    }
}
