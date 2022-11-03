package no.kristiania;

import org.glassfish.jersey.server.ResourceConfig;

import java.util.Map;

class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        super(BooksEndpoint.class);
        setProperties(Map.of("jersey.config.server.wadl.disableWadl", "true"));
    }
}
