package no.kristiania;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Map;

class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        super(BooksEndpoint.class);
        setProperties(Map.of("jersey.config.server.wadl.disableWadl", "true"));
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BookDao.class).to(BookDao.class);
            }
        });
    }
}
