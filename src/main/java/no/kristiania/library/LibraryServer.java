package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryServer {
    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    private final Server server = new Server(9080);

    public void start() throws Exception {
        server.start();
        logger.info("Started server on {}", server.getURI());
    }

    public static void main(String[] args) throws Exception {
        new LibraryServer().start();
    }
}
