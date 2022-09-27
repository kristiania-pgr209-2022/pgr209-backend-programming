package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
        var response = "Hello world";
        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                              "Content-Length: " + response.length() + "\r\n" +
                                              "Connection: close\r\n" +
                                              "\r\n" + response).getBytes());
    }
}
