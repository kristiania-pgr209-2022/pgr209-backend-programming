package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

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
        try {
            var request = new HttpMessage(clientSocket);
            System.out.println(request.getStartLine());
            var requestTarget = request.getStartLine().split(" ")[1];
            var targetParts = requestTarget.split("\\?", 2);
            var queryParameters = new HashMap<String, String>();
            if (targetParts.length >= 2) {
                var query = targetParts[1];
                requestTarget = targetParts[0];
                for (String queryParameter : query.split("&")) {
                    var parameterParts = queryParameter.split("=", 2);
                    queryParameters.put(
                            URLDecoder.decode(parameterParts[0], StandardCharsets.UTF_8),
                            URLDecoder.decode(parameterParts[1], StandardCharsets.UTF_8)
                    );
                }

            }

            if (requestTarget.equals("/api/echo")) {
                var body = queryParameters.get("input-string");
                clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                      "Connection: close\r\n" +
                                                      "Content-type: text/plain; charset=utf-8\r\n" +
                                                      "Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                                                      "\r\n" + body).getBytes(StandardCharsets.UTF_8));
                return;
            }

            var requestPath = serverRoot.resolve(requestTarget.substring(1));
            if (Files.isDirectory(requestPath)) {
                requestPath = requestPath.resolve("index.html");
            }
            if (Files.exists(requestPath)) {
                var body = Files.readString(requestPath);
                clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                      "Connection: close\r\n" +
                                                      "Content-Length: " + body.length() + "\r\n" +
                                                      "\r\n" + body).getBytes(StandardCharsets.UTF_8));
            } else {
                var responseBody = "Unknown URL '" + requestTarget + "'";
                clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                                      "Connection: close\r\n" +
                                                      "Content-Type: text/plain\r\n" +
                                                      "Content-Length: " + responseBody.length() + "\r\n" +
                                                      "\r\n" +
                                                      responseBody +
                                                      "\r\n").getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            clientSocket.getOutputStream().write(("HTTP/1.1 500 SERVER ERROR\r\n" +
                                                  "Connection: close\r\n" +
                                                  "Content-Type: text/plain\r\n" +
                                                  "Content-Length: " + 0 + "\r\n" +
                                                  "\r\n").getBytes(StandardCharsets.UTF_8));
            e.printStackTrace();
        }

    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public static void main(String[] args) throws IOException {
        var server = new HttpServer(9080, Path.of("src/main/resources"));
    }

}
