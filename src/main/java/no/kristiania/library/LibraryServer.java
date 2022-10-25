package no.kristiania.library;

import jakarta.persistence.Persistence;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;

public class LibraryServer {
    private final Server server;

    public LibraryServer(int port, DataSource dataSource) throws NamingException, IOException {
        this.server = new Server(port);

        var handler = new ServletContextHandler();
        handler.setContextPath("/");

        new Resource("jdbc/dataSource", dataSource);
        var entityManagerFactory = Persistence.createEntityManagerFactory("library");

        var config = new LibraryResourceConfig(entityManagerFactory);
        handler.addServlet(new ServletHolder(new ServletContainer(config)), "/api/*");
        handler.addFilter(new FilterHolder(new EntityManagerFilter(config)), "/api/*", EnumSet.of(DispatcherType.REQUEST));
        server.setHandler(handler);
    }

    public void start() throws Exception {
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

}
