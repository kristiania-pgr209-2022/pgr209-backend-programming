package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class HttpMessage {
    private final String startLine;
    private final Map<String, String> headers;
    String body;
    int contentLength;

    public HttpMessage(Socket socket) throws IOException {
        startLine = readLine(socket);
        headers = readHeaders(socket);
        contentLength = Integer.parseInt(getHeader("Content-Length"));
        body = readBody(socket);
    }

    public String getStartLine() {
        return startLine;
    }

    public String getHeader(String fieldName) {
        return headers.get(fieldName);
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

    private Map<String, String> readHeaders(Socket socket) throws IOException {
        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()) {
            String[] parts = headerLine.split(":\\s*");
            headers.put(parts[0], parts[1]);
        }
        return headers;
    }

    private String readBody(Socket socket) throws IOException {
        var body = new byte[contentLength];
        for (int i = 0; i < body.length; i++) {
            body[i] = (byte) socket.getInputStream().read();
        }
        return new String(body, StandardCharsets.UTF_8);
    }

}
