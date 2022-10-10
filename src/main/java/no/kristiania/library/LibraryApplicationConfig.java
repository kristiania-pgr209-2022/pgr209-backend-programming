package no.kristiania.library;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

class LibraryApplicationConfig extends ResourceConfig {
    public LibraryApplicationConfig() {
        super(BookResource.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BookRepository.class).to(BookRepository.class);
            }
        });
    }
}
