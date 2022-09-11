package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private final Map<String, String> headers = new HashMap<>();
    private final int status;
    private final String body;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);
        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                         "Host: " + host + "\r\n" +
                         "\r\n";
        socket.getOutputStream().write(request.getBytes());

        String[] responseLine = readLine(socket).split(" ");

        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()) {
            String[] headerParts = headerLine.split("\s*:\s*");
            headers.put(headerParts[0], headerParts[1]);
        }

        status = Integer.parseInt(responseLine[1]);

        StringBuilder body = new StringBuilder();
        for (int i = 0; i < getContentLength(); i++) {
            body.append((char) socket.getInputStream().read());
        }
        this.body = body.toString();
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

    public int getStatus() {
        return status;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    public String getBody() {
        return body;
    }

    public static void main(String[] args) throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        System.out.println(client.getBody());
    }
}
