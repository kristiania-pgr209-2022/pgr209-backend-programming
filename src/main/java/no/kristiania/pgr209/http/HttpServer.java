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
            var request = new HttpMessage(clientSocket.getInputStream());
            String requestTarget = request.getStartLine().split(" ")[1];
            String body = "File not found " + requestTarget;
            clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                                  "Content-Length: " + body.length() + "\r\n" +
                                                  "\r\n" +
                                                  body).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
