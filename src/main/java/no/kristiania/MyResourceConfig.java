package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.eclipse.jetty.plus.jndi.Resource;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.NamingException;
import java.util.Map;

class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() throws NamingException {
        super(BooksEndpoint.class);
        setProperties(Map.of("jersey.config.server.wadl.disableWadl", "true"));
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;MODE=LEGACY;DB_CLOSE_DELAY=-1");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
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
