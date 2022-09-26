package no.kristiania.pgr209;

import org.eclipse.jetty.server.Server;

public class LibraryServer {

    private final Server server = new Server(9080);

    public static void main(String[] args) throws Exception {
        new LibraryServer().start();
    }

    private void start() throws Exception {
        server.start();
    }

}
