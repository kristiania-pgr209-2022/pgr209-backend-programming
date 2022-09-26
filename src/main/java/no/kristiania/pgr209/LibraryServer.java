package no.kristiania.pgr209;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class LibraryServer {

    private final Server server = new Server(9080);

    public static void main(String[] args) throws Exception {
        new LibraryServer().start();
    }

    private void start() throws Exception {
        var webapp = new WebAppContext(Resource.newClassPathResource("/webapp"), "/");
        webapp.addServlet(new ServletHolder(new AddBookServlet()), "/api/addBook");
        server.setHandler(webapp);

        server.start();
    }

}
