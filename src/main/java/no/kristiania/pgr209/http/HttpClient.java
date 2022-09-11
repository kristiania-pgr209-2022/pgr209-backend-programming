package no.kristiania.pgr209.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    private int status;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        var socket = new Socket("httpbin.org", 80);
        String request = "GET /html HTTP/1.1\r\n" +
                         "Host: httpbin.org\r\n" +
                         "\r\n";
        socket.getOutputStream().write(request.getBytes());
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            System.out.print((char)c);
        }


        status = 200;
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

}
