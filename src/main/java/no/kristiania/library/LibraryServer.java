package no.kristiania.library;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {

    private final Server server;

    public LibraryServer(int port) {
        this.server = new Server(port);

        server.setHandler(new HandlerList(
                createApiContext(),
                new WebAppContext(Resource.newClassPathResource("/webapp"), "/")
        ));
    }

    private Handler createApiContext() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/api");
        context.addServlet(new ServletHolder(new ServletContainer(
                new ResourceConfig(BookResource.class, AutoScanFeature.class)
        )), "/*");
        return context;
    }

    void start() throws Exception {
        server.start();
    }

    public static void main(String[] args) throws Exception {
        new LibraryServer(8080).start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }
}
