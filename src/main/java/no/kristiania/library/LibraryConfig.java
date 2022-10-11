package no.kristiania.library;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class LibraryConfig extends ResourceConfig {
    public LibraryConfig() {
        super(BookResource.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BookRepositoryImpl.class).to(BookRepository.class)
                        .in(Singleton.class);
            }
        });
        Map<String, String> props = new HashMap<>();
        props.put("jersey.config.server.wadl.disableWadl", "true");
        setProperties(props);
    }
}
