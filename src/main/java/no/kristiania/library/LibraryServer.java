package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {
    private final Server server;

    public LibraryServer(int port, DataSource dataSource) {
        this.server = new Server(port);

        var handler = new WebAppContext();
        handler.setContextPath("/");
        handler.setBaseResource(Resource.newClassPathResource("/webapp"));
        handler.addServlet(new ServletHolder(new ServletContainer(new LibraryResourceConfig(dataSource))), "/api/*");
        server.setHandler(handler);
    }

    public void start() throws Exception {
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

}
