package no.kristiania.library;

import no.kristiania.library.database.BookDao;
import no.kristiania.library.database.jdbc.JdbcBookDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

class LibraryResourceConfig extends ResourceConfig {
    public LibraryResourceConfig(DataSource dataSource) {
        super(BookResource.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcBookDao.class).to(BookDao.class);
                bindFactory(() -> dataSource).to(DataSource.class);
            }
        });
    }
}
