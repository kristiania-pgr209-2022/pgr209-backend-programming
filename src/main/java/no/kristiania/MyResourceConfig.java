package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        super(BookEndpoint.class);

        var entityManagerFactory = Persistence.createEntityManagerFactory("library");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BookDao.class).to(BookDao.class);
                bindFactory(entityManagerFactory::createEntityManager)
                        .to(EntityManager.class);
            }
        });
    }
}
