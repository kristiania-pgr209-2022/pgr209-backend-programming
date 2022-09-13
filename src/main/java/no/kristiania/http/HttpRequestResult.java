package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class HttpRequestResult {

    private HttpMessage response = new HttpMessage();
    private final Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final int statusCode;
    private final int contentLength;
    private final String body;

    public HttpRequestResult(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);

        socket.getOutputStream().write(
                ("GET " + requestTarget + " HTTP/1.1\r\n" +
                 "Connection: close\r\n" +
                 "Host: " + host + "\r\n" +
                 "\r\n").getBytes(StandardCharsets.UTF_8)
        );

        String statusLine = readLine(socket);
        statusCode = Integer.parseInt(statusLine.split(" ")[1]);

        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()) {
            String[] parts = headerLine.split(":\\s*");
            headers.put(parts[0], parts[1]);
        }

        contentLength = Integer.parseInt(getHeader("Content-Length"));

        var body = new byte[contentLength];
        for (int i = 0; i < body.length; i++) {
            body[i] = (byte) socket.getInputStream().read();
        }
        this.body = new String(body, StandardCharsets.UTF_8);
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            line.append((char)c);
        }
        c = socket.getInputStream().read(); // read the next \n
        return line.toString();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getHeader(String fieldName) {
        return headers.get(fieldName);
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getBody() {
        return body;
    }

    public static void main(String[] args) throws IOException {
        var client = new HttpRequestResult("httpbin.org", 80, "/html");
        System.out.println(client.getBody());
    }

}
