package no.kristiania.library;

import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class LibraryServer {

    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    private final Server server;

    public LibraryServer(int port) {
        this.server = new Server(port);

        server.setHandler(new HandlerList(
                createApiContext(),
                new WebAppContext(Resource.newClassPathResource("/webapp"), "/")
        ));
        server.setRequestLog(new CustomRequestLog());
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
        logger.info("Started server on {}", getURL());
    }

    public static void main(String[] args) throws Exception {
        int port = Optional.ofNullable(System.getenv("HTTP_PLATFORM_PORT")).map(Integer::parseInt)
                .orElse(8080);
        new LibraryServer(port).start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }
}
