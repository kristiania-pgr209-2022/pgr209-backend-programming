package no.kristiania.library;

import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {
    private final Server server;

    public LibraryServer(int port, DataSource dataSource) throws NamingException, IOException {
        this.server = new Server(port);

        var handler = new ServletContextHandler();
        handler.setContextPath("/");
        handler.addServlet(new ServletHolder(new ServletContainer(
                new LibraryResourceConfig(dataSource)
        )), "/api/*");
        server.setHandler(handler);
    }

    public void start() throws Exception {
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

}
