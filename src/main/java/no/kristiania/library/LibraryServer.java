package no.kristiania.library;

import jakarta.persistence.Persistence;
import jakarta.servlet.DispatcherType;
import no.kristiania.library.database.Database;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;

public class LibraryServer {
    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    private final Server server;

    public LibraryServer(int port, DataSource dataSource) throws NamingException, IOException {
        this.server = new Server(port);

        var handler = new WebAppContext();
        handler.setContextPath("/");
        var baseResource = org.eclipse.jetty.util.resource.Resource.newClassPathResource("/webapp");
        org.eclipse.jetty.util.resource.Resource sourceResource = getSourceResource(baseResource);

        if (sourceResource != null) {
            handler.setBaseResource(sourceResource);
            handler.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            handler.setBaseResource(baseResource);
        }

        new Resource("jdbc/dataSource", dataSource);
        var entityManagerFactory = Persistence.createEntityManagerFactory("library");

        var config = new LibraryResourceConfig(entityManagerFactory);
        handler.addServlet(new ServletHolder(new ServletContainer(config)), "/api/*");
        handler.addFilter(new FilterHolder(new EntityManagerFilter(config)), "/api/*", EnumSet.of(DispatcherType.REQUEST));
        server.setHandler(handler);
    }

    private org.eclipse.jetty.util.resource.Resource getSourceResource(org.eclipse.jetty.util.resource.Resource baseResource) throws IOException {
        return org.eclipse.jetty.util.resource.Resource.newResource(baseResource.getFile().toString()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources")
        );
    }

    public void start() throws Exception {
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public static void main(String[] args) throws Exception {
        var server = new LibraryServer(8080, Database.getDataSource());
        server.start();
        logger.info("Server started on {}", server.getURL());
    }

}
