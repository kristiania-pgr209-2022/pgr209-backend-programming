package no.kristiania.pgr209;

import no.kristiania.pgr209.books.BookRepository;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

public class LibraryServer {

    private final Server server = new Server(9080);

    private final BookRepository bookRepository = BookRepository.getInstance();

    public static void main(String[] args) throws Exception {
        new LibraryServer().start();
    }

    private void start() throws Exception {
        var resources = Resource.newClassPathResource("/webapp");
        var webapp = new WebAppContext(resources, "/");

        var srcDirectory = new File(resources.getFile().toString().replaceAll("\\\\", "/").replace("/target/classes/", "/src/main/resources/"));
        if (srcDirectory.isDirectory()) {
            webapp.setBaseResource(Resource.newResource(srcDirectory));
            webapp.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        }

        webapp.addServlet(new ServletHolder(new AddBookServlet(bookRepository)), "/api/addBook");
        webapp.addServlet(new ServletHolder(new ListBooksServlet(bookRepository)), "/api/listBooks");
        webapp.addServletContainerInitializer(new JettyJasperInitializer());
        server.setHandler(webapp);

        server.start();
    }

}
