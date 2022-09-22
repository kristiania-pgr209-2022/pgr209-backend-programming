package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {

    private ServerSocket serverSocket;
    private final Path serverRoot;

    public HttpServer(int port, Path serverRoot) throws IOException {
        serverSocket = new ServerSocket(port);
        this.serverRoot = serverRoot;
        start();
    }

    private void start() {
        new Thread(() -> {
            while (true) {
                try {
                    var clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("Server started");
    }

    private void handleClient(Socket clientSocket) throws IOException {
        var request = new HttpMessage(clientSocket);
        System.out.println(request.getStartLine());
        var requestTarget = request.getStartLine().split(" ")[1];
        var requestPath = serverRoot.resolve(requestTarget.substring(1));
        if (Files.exists(requestPath)) {
            var body = Files.readString(requestPath);
            clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                  "Content-Length: " + body.length() + "\r\n" +
                                                  "\r\n" + body).getBytes(StandardCharsets.UTF_8));
        } else {
            var responseBody = "Unknown URL '" + requestTarget + "'";
            clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                                  "Content-Type: text/plain\r\n" +
                                                  "Content-Length: " + responseBody.length() + "\r\n" +
                                                  "\r\n" +
                                                  responseBody +
                                                  "\r\n").getBytes(StandardCharsets.UTF_8));
        }

    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public static void main(String[] args) throws IOException {
        var server = new HttpServer(9080, Path.of("src/main/resources"));
    }

}
