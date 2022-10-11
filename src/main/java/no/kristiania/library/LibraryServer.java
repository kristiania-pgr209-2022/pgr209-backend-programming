package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {

    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    private final Server server;

    public LibraryServer(int port) {
        this.server = new Server(port);

        server.setHandler(new HandlerList(
                createApiContext(),
                new WebAppContext(Resource.newClassPathResource("/webapp"), "/")
        ));
    }

    private ServletContextHandler createApiContext() {
        var context = new ServletContextHandler(server, "/api");
        context.addServlet(new ServletHolder(new ServletContainer(
                new LibraryConfig()
        )), "/*");
        return context;
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
        logger.info("Started server at {}", getURL());
    }

    public static void main(String[] args) throws Exception {
        new LibraryServer(8080).start();
    }

}
