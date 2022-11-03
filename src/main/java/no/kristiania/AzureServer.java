package no.kristiania;

import org.eclipse.jetty.server.Server;
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

public class AzureServer {
    private static final Logger logger = LoggerFactory.getLogger(AzureServer.class);
    private final Server server;

    public AzureServer(int port) {
        server = new Server(port);
        var context = createWebAppContext();
        context.addServlet(new ServletHolder(new ServletContainer(new ResourceConfig(BooksEndpoint.class))), "/api/*");
        server.setHandler(context);
    }

    private WebAppContext createWebAppContext() {
        var context = new WebAppContext();
        context.setContextPath("/");
        context.setBaseResource(Resource.newClassPathResource("/azure-demo-webapp"));
        return context;
    }

    public void start() throws Exception {
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public static void main(String[] args) throws Exception {
        var port = Optional.ofNullable(System.getenv("HTTP_PLATFORM_PORT"))
                .map(Integer::parseInt)
                .orElse(8080);
        var server = new AzureServer(port);
        server.start();
        logger.info("Started at {}", server.getURL());
    }
}
