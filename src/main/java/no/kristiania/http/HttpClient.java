package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class HttpClient {

    private final Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final int statusCode;
    private int contentLength;
    private String body;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);

        socket.getOutputStream().write(
                ("GET " + requestTarget + " HTTP/1.1\r\n" +
                 "Connection: close\r\n" +
                 "Host: " + host + "\r\n" +
                 "\r\n").getBytes()
        );

        String statusLine = readLine(socket);
        statusCode = Integer.parseInt(statusLine.split(" ")[1]);

        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()) {
            String[] parts = headerLine.split(":\\s*");
            headers.put(parts[0], parts[1]);
        }

        contentLength = Integer.parseInt(getHeader("Content-Length"));

        body = "";
        for (int i = 0; i < contentLength; i++) {
            body += (char)socket.getInputStream().read();
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
                ("GET /html HTTP/1.1\r\n" +
                 "Connection: close\r\n" +
                 "Host: httpbin.org\r\n" +
                 "\r\n").getBytes()
        );

        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char)c);
        }

    }

    public int getContentLength() {
        return contentLength;
    }

    public String getBody() {
        return body;
    }
}
