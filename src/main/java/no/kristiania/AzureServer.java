package no.kristiania;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Properties;

public class AzureServer {
    private static final Logger logger = LoggerFactory.getLogger(AzureServer.class);
    private final Server server;

    public AzureServer(int port, DataSource dataSource) throws NamingException {
        server = new Server(port);
        var context = createWebAppContext();
        context.addServlet(new ServletHolder(new ServletContainer(new MyResourceConfig(dataSource))), "/api/*");
        context.addFilter(new FilterHolder(new EntityManagerFilter()), "/api/*", EnumSet.of(DispatcherType.REQUEST));
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
        var server = new AzureServer(port, createDataSource());
        server.start();
        logger.info("Started at {}", server.getURL());
    }

    private static DataSource createDataSource() throws IOException, SQLException {
        var properties = new Properties();
        try (var config = new FileInputStream("application.properties")) {
            properties.load(config);
        }

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getProperty("dataSource.url"));
        dataSource.setUsername(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        dataSource.setLoginTimeout(2000);

        Flyway.configure().dataSource(dataSource).load().migrate();

        return dataSource;
    }
}
