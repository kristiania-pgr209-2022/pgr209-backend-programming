package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private final HttpMessage responseMessage = new HttpMessage();
    private final Map<String, String> headers;
    private final int status;
    private final String body;
    private final String reasonPhrase;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);
        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                         "Host: " + host + "\r\n" +
                         "\r\n";
        socket.getOutputStream().write(request.getBytes());

        String[] responseLine = HttpMessage.readLine(socket).split(" ", 3);
        status = Integer.parseInt(responseLine[1]);
        reasonPhrase = responseLine[2];

        this.headers = readHeaders(socket);


        StringBuilder body = new StringBuilder();
        for (int i = 0; i < getContentLength(); i++) {
            body.append((char) socket.getInputStream().read());
        }
        this.body = body.toString();
    }

    public static Map<String, String> readHeaders(Socket socket) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        String headerLine;
        while(!(headerLine = HttpMessage.readLine(socket)).isEmpty()) {
            String[] headerParts = headerLine.split("\s*:\s*", 2);
            headers.put(headerParts[0], headerParts[1]);
        }
        return headers;
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

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
