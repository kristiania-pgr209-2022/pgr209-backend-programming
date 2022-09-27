package no.kristiania.http;

import java.io.IOException;
import java.io.InputStream;
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
        var requestLine = readLine(clientSocket.getInputStream());
        var parts = requestLine.split(" ");
        var requestTarget = parts[1];


        var response = "I still haven't found what you're looking for: " + requestTarget;
        clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                              "Content-Length: " + (response.getBytes(StandardCharsets.UTF_8).length) + "\r\n" +
                                              "Connection: close\r\n" +
                                              "Content-Type: text/html; charset=utf-8\r\n" +
                                              "\r\n" + response).getBytes(StandardCharsets.UTF_8));
    }

    private String readLine(InputStream inputStream) throws IOException {
        var line = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != '\r') {
            line.append((char)c);
        }
        return line.toString();
    }
}
