package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    private int statusCode;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);

        socket.getOutputStream().write(
                ("GET " + requestTarget + " HTTP/1.1\r\n" +
                 "Connection: close\r\n" +
                 "Host: " + host + "\r\n" +
                 "\r\n").getBytes()
        );

        StringBuilder line = readLine(socket);
        statusCode = Integer.parseInt(line.toString().split(" ")[1]);
    }

    private StringBuilder readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            line.append((char)c);
        }
        System.out.println(line);
        return line;
    }


    public int getStatusCode() {
        return statusCode;
    }


    public static void main(String[] args) throws IOException {
        var socket = new Socket("httpbin.org", 80);

        socket.getOutputStream().write(
                ("GET /bullshit HTTP/1.1\r\n" +
                 "Connection: close\r\n" +
                 "Host: httpbin.org\r\n" +
                 "\r\n").getBytes()
        );

        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char)c);
        }

    }
}
