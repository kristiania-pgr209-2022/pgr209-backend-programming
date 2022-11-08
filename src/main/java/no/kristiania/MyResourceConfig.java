package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.eclipse.jetty.plus.jndi.Resource;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.naming.NamingException;
import javax.sql.DataSource;

class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig(DataSource dataSource) throws NamingException {
        super(BookEndpoint.class);

        new Resource("jdbc/dataSource", dataSource);
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
