package no.kristiania.library;

import jakarta.inject.Singleton;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {
    private final Server server;

    public LibraryServer(int port, DataSource dataSource) {
        this.server = new Server(port);
        var handler = new ServletContextHandler();
        handler.setContextPath("/");
        var resourceConfig = new ResourceConfig(BooksEndpoint.class) {
            {
                register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(BookRepository.class).to(BookRepository.class).in(Singleton.class);
                    }
                });
            }
        };
        handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig)), "/api/*");
        server.setHandler(handler);
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
    }
}
