package no.kristiania;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        super(BookEndpoint.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BookDao.class).to(BookDao.class);
            }
        });
    }
}
