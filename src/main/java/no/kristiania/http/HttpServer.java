package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;

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
                serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
