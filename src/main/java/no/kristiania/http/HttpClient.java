package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private Map<String, String> headers = new HashMap<>();
    private final int statusCode;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);

        socket.getOutputStream().write(
                ("GET " + requestTarget + " HTTP/1.1\r\n" +
                 "Connection: close\r\n" +
                 "Host: " + host + "\r\n" +
                 "\r\n").getBytes()
        );

        String line = readLine(socket);
        statusCode = Integer.parseInt(line.split(" ")[1]);

        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()) {
            String[] parts = headerLine.split(":\\s*");
            headers.put(parts[0], parts[1]);
        }
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            line.append((char)c);
        }
        c = socket.getInputStream().read(); // read the next \n
        System.out.println(line);
        return line.toString();
    }


    public int getStatusCode() {
        return statusCode;
    }

    public String getHeader(String fieldName) {
        return headers.get(fieldName);
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
