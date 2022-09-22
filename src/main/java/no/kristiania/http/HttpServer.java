package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    private ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world");

        var serverSocket = new ServerSocket(9080);

        var clientSocket = serverSocket.accept();

        var request = new HttpMessage(clientSocket);
        System.out.println(request.getStartLine());
        System.out.println(request.headers);

        var body = "<html><h1>Hello world!</h1></html>";
        var contentLength = body.getBytes().length;
        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                                              "Content-Type: text/html\r\n" +
                                              "Content-Length: " + contentLength + "\r\n" +
                                              "\r\n" +
                                              body).getBytes(StandardCharsets.UTF_8));

    }

}
