package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    private ServletContextHandler createApiContext() {
        var context = new ServletContextHandler(server, "/api");
        context.addServlet(new ServletHolder(new ServletContainer(
                new ResourceConfig(BookResource.class)
        )), "/*");
        return context;
    }

    public static void main(String[] args) {
        System.out.println("Hello world");
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
    }
}
