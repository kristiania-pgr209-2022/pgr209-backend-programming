package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {

    private final Server server;

    public LibraryServer(int port) {
        this.server = new Server(port);

        server.setHandler(new HandlerList(
                new WebAppContext(Resource.newClassPathResource("/webapp"), "/")
        ));
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
