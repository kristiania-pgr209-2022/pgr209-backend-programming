package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private Map<String, String> headers = new HashMap<>();
    private final int status;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);
        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                         "Host: " + host + "\r\n" +
                         "\r\n";
        socket.getOutputStream().write(request.getBytes());

        String[] responseLine = readLine(socket).split(" ");

        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()) {
            String[] headerParts = headerLine.split(":");
            headers.put(headerParts[0], headerParts[1]);
        }

        status = Integer.parseInt(responseLine[1]);
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            line.append((char)c);
        }
        socket.getInputStream().read(); // read next \n character
        return line.toString();
    }

    public static void main(String[] args) throws IOException {
        var socket = new Socket("httpbin.org", 80);
        String request = "GET /html HTTP/1.1\r\n" +
                   "Host: httpbin.org\r\n" +
                   "\r\n";
        socket.getOutputStream().write(request.getBytes());
        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char)c);
        }
    }

    public int getStatus() {
        return status;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
}
