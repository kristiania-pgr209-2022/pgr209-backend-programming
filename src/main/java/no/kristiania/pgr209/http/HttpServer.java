package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {
    private final ServerSocket serverSocket;
    private Path root = Path.of("");

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
            if (Files.exists(root.resolve(requestTarget.substring(1)))) {
                String body = Files.readString(root.resolve(requestTarget.substring(1)));
                clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                      "Content-Length: " + body.length() + "\r\n" +
                                                      "\r\n" +
                                                      body).getBytes());
            } else {
                String body = "File not found " + requestTarget;
                clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                                      "Content-Length: " + body.length() + "\r\n" +
                                                      "\r\n" +
                                                      body).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRoot(Path root) {
        this.root = root;
    }
}
