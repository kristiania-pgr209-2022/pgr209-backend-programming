package no.kristiania.http;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;

public class HttpServer {

    private ServerSocket serverSocket;
    private final Path serverRoot;

    public HttpServer(int port, Path serverRoot) throws IOException {
        this(serverRoot, new ServerSocket(port));
    }

    public HttpServer(Path serverRoot, ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
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
            if (requestTarget.equals("/api/login")) {
                var username = queryParameters.get("username");
                var body = "Ok";
                clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                      "Connection: close\r\n" +
                                                      "Set-Cookie: authenticatedUserName=" + username + "; HttpOnly; Path=/\r\n" +
                                                      "Content-type: text/plain; charset=utf-8\r\n" +
                                                      "Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                                                      "\r\n" + body).getBytes(StandardCharsets.UTF_8));
                return;
            }
            if (requestTarget.equals("/api/userinfo")) {
                var cookies = request.getHeader("Cookie");
                if (cookies != null) {
                    var cookieMap = new HashMap<String, String>();
                    for (String c : cookies.split(";")) {
                        var part = c.split("=", 2);
                        cookieMap.put(part[0], part[1]);
                    }

                    var body = cookieMap.get("authenticatedUserName");
                    if (body != null) {
                        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                              "Connection: close\r\n" +
                                                              "Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                                                              "\r\n" + body).getBytes(StandardCharsets.UTF_8));
                        return;
                    }
                }
            }

            var requestPath = serverRoot.resolve(requestTarget.substring(1));
            if (Files.isDirectory(requestPath)) {
                requestPath = requestPath.resolve("index.html");
            }
            if (Files.exists(requestPath)) {
                clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                                      "Connection: close\r\n" +
                                                      "Content-Length: " + requestPath.toFile().length() + "\r\n" +
                                                      "\r\n").getBytes(StandardCharsets.UTF_8));
                try (var fileInputStream = new FileInputStream(requestPath.toFile())) {
                    fileInputStream.transferTo(clientSocket.getOutputStream());
                }
            } else {
                var responseBody = "Unknown URL '" + requestTarget + "'";
                clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                                                      "Connection: close\r\n" +
                                                      "Content-Type: text/plain\r\n" +
                                                      "Content-Length: " + responseBody.length() + "\r\n" +
                                                      "\r\n" +
                                                      responseBody +
                                                      "\r\n").getBytes(StandardCharsets.UTF_8));
                System.out.println("404 NOT FOUND");
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

    // keytool -genkeypair -keystore servercert.p12 -dname cn=localhost -storepass abc123 -keyalg RSA -ext san=dns:localhost -ext san=dns:kristiania.no
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        var sslContext = SSLContext.getInstance("TLS");
        var keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        var keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(new FileInputStream("servercert.p12"), "abc123".toCharArray());
        keyManagerFactory.init(keyStore, "abc123".toCharArray());
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        var serverSocket = sslContext.getServerSocketFactory().createServerSocket(443);
        var server = new HttpServer(Path.of("src/main/resources"), serverSocket);
    }

}
