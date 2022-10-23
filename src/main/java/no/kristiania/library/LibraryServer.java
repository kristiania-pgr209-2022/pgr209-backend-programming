package no.kristiania.library;

import jakarta.persistence.Persistence;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;

public class LibraryServer {
    private final Server server;

    public LibraryServer(int port, DataSource dataSource) throws NamingException {
        this.server = new Server(port);

        new org.eclipse.jetty.plus.jndi.Resource("jdbc/dataSource", dataSource);

        var handler = new WebAppContext();
        handler.setContextPath("/");
        handler.setBaseResource(Resource.newClassPathResource("/webapp"));
        handler.addServlet(new ServletHolder(new ServletContainer(new LibraryResourceConfig())), "/api/*");
        handler.addFilter(new FilterHolder(new TransactionFilter(
                Persistence.createEntityManagerFactory("library")
        )), "/*", EnumSet.of(DispatcherType.REQUEST));
        server.setHandler(handler);
    }

    public void start() throws Exception {
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

}
