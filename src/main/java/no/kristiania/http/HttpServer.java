package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    private final ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        new HttpServer(9080).start();
    }

    private void start() {
        new Thread(() -> {
            try {
                var clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleRequest(Socket clientSocket) throws IOException {
        var response = "<h1>Hållæ verden!</h1>";
        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                              "Content-Length: " + (response.getBytes(StandardCharsets.UTF_8).length) + "\r\n" +
                                              "Connection: close\r\n" +
                                              "Content-Type: text/html; charset=utf-8\r\n" +
                                              "\r\n" + response).getBytes(StandardCharsets.UTF_8));
    }
}
