package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    private final HttpMessage responseMessage;
    private final int status;
    private final String reasonPhrase;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket(host, port);
        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                         "Host: " + host + "\r\n" +
                         "\r\n";
        socket.getOutputStream().write(request.getBytes());

        responseMessage = new HttpMessage(socket.getInputStream());

        String[] responseLine = responseMessage.getStartLine().split(" ", 3);
        status = Integer.parseInt(responseLine[1]);
        reasonPhrase = responseLine[2];
    }

    public int getStatus() {
        return status;
    }

    public String getHeader(String name) {
        return responseMessage.headers.get(name);
    }

    public String getBody() {
        return responseMessage.getBody();
    }

    public static void main(String[] args) throws IOException {
        var client = new HttpClient("httpbin.org", 80, "/html");
        System.out.println(client.getBody());
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
