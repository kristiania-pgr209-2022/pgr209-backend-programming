package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        while (!Thread.interrupted()) {
            try {
                var clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRequest(Socket clientSocket) throws IOException {
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
    }

    public void setRoot(Path root) {
        this.root = root;
    }

    public static void main(String[] args) throws IOException {
        new HttpServer(9080).start();
    }

}
