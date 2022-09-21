package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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
                try {
                    handleClientRequest(clientSocket);
                } catch (Exception e) {
                    String body = "Server error " + e;
                    clientSocket.getOutputStream().write(("HTTP/1.1 500 SERVER ERROR\r\n" +
                                                          "Content-Length: " + body.length() + "\r\n" +
                                                          "Connection: close\r\n" +
                                                          "\r\n" +
                                                          body).getBytes());
                    System.out.println("500 SERVER ERROR");
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClientRequest(Socket clientSocket) throws IOException {
        var request = new HttpMessage(clientSocket.getInputStream());
        String requestTarget = request.getStartLine().split(" ")[1];

        var queryPos = requestTarget.indexOf('?');
        String requestFile = requestTarget;
        Map<String, String> queryParameters = new HashMap<>();
        if (queryPos >= 0) {
            requestFile = requestTarget.substring(0, queryPos);
            String query = requestTarget.substring(queryPos+1);
            for (String queryParameter : query.split("&")) {
                String[] parts = queryParameter.split("=",2);
                queryParameters.put(
                        URLDecoder.decode(parts[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
                );
            }

        }

        var targetPath = root.resolve(requestFile.substring(1));
        if (Files.isDirectory(targetPath)) {
            targetPath = targetPath.resolve("index.html");
        }
        System.out.println("Requesting " + targetPath);
        if (Files.exists(targetPath)) {
            String body = Files.readString(targetPath);
            String contentType = getContentType(targetPath);
            clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                  "Content-Length: " + body.length() + "\r\n" +
                                                  "Content-Type: " + contentType + "\r\n" +
                                                  "Connection: close\r\n" +
                                                  "\r\n" +
                                                  body).getBytes());
            System.out.println(targetPath + " 200 OK");
        } else if (requestFile.equals("/echo")) {
            String body = queryParameters.getOrDefault("input-string", "hello");
            String contentType = queryParameters.getOrDefault("content-type", "text/plain");
            clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                  "Content-Length: " + body.length() + "\r\n" +
                                                  "Content-Type: " + contentType + "\r\n" +
                                                  "Connection: close\r\n" +
                                                  "\r\n" +
                                                  body).getBytes());
            System.out.println(targetPath + " 200 OK");
        } else {
            String body = "File not found " + requestFile;
            clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                                  "Content-Length: " + body.length() + "\r\n" +
                                                  "Connection: close\r\n" +
                                                  "\r\n" +
                                                  body).getBytes());
            System.out.println(targetPath + " 404 NOT FOUND");
        }
    }

    private String getContentType(Path targetPath) {
        var filename = targetPath.getFileName().toString();
        var lastPeriodPos = filename.lastIndexOf('.');
        if (lastPeriodPos < 0) {
            return null;
        }
        var extension = filename.substring(lastPeriodPos+1);
        return switch (extension) {
            case "html" -> "text/html";
            case "txt" -> "text/plain";
            case "css" -> "text/css";
            default -> null;
        };
    }

    public void setRoot(Path root) {
        this.root = root;
    }

    public static void main(String[] args) throws IOException {
        var httpServer = new HttpServer(9080);
        httpServer.setRoot(Path.of("src/main/resources"));
        httpServer.start();
    }

}
