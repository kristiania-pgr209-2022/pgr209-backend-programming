package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {
    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    private final Server server;

    public LibraryServer(int port) throws IOException {
        this.server = new Server(port);
        server.setHandler(createWebApp());
    }

    private static WebAppContext createWebApp() throws IOException {
        WebAppContext webContext = new WebAppContext();
        webContext.setContextPath("/");

        Resource resources = Resource.newClassPathResource("/webapp");
        var sourceDirectory = new File(resources.getFile().getAbsoluteFile().toString()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources"));
        if (sourceDirectory.isDirectory()) {
            webContext.setBaseResource(Resource.newResource(sourceDirectory));
            webContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            webContext.setBaseResource(resources);
        }

        webContext.addServlet(new ServletHolder(new ListBooksServlet()), "/api/books");
        return webContext;
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
    }

    public static void main(String[] args) throws Exception {
        var server = new LibraryServer(8080);
        server.start();
        logger.warn("Server starting at {}", server.getURL());
    }
}
