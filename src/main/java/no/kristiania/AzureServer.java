package no.kristiania;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.MalformedURLException;
import java.net.URL;

public class AzureServer {
    private final Server server;

    public AzureServer(int port) {
        server = new Server(port);
        server.setHandler(createWebAppContext());
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
}
