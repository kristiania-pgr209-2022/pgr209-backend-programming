package no.kristiania;

import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;
import java.util.Optional;

public class AzureServer {
    private static final Logger logger = LoggerFactory.getLogger(AzureServer.class);
    private final Server server;

    public AzureServer(int port) throws NamingException {
        server = new Server(port);
        var context = createWebAppContext();
        var resourceConfig = new MyResourceConfig();
        context.addServlet(new ServletHolder(new ServletContainer(resourceConfig)), "/api/*");
        context.addFilter(new FilterHolder(new TransactionFilter(resourceConfig)), "/api/*", EnumSet.of(DispatcherType.REQUEST));
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
