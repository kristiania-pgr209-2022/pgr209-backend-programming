package no.kristiania.pgr209;

import no.kristiania.pgr209.books.BookRepository;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class LibraryServer {

    private final Server server = new Server(9080);

    private final BookRepository bookRepository = new BookRepository();

    public static void main(String[] args) throws Exception {
        new LibraryServer().start();
    }

    private void start() throws Exception {
        var webapp = new WebAppContext(Resource.newClassPathResource("/webapp"), "/");
        webapp.addServlet(new ServletHolder(new AddBookServlet(bookRepository)), "/api/addBook");
        webapp.addServlet(new ServletHolder(new ListBooksServlet(bookRepository)), "/api/listBooks");
        server.setHandler(webapp);

        server.start();
    }

}
