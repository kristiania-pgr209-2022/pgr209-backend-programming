package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {
    private final ServerSocket serverSocket;

    public HttpServer(int serverPort) throws IOException {
        this.serverSocket = new ServerSocket(serverPort);
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void start() {
        new Thread(this::handleClients).start();
    }

    private void handleClients() {
        try {
            var clientSocket = serverSocket.accept();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
