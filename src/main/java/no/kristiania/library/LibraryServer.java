package no.kristiania.library;

import org.eclipse.jetty.server.Server;

public class LibraryServer {

    private final Server server;

    public LibraryServer(int port) {
        this.server = new Server(port);
    }

    private void start() throws Exception {
        server.start();
    }

    public static void main(String[] args) throws Exception {
        new LibraryServer(8080).start();
    }
}
