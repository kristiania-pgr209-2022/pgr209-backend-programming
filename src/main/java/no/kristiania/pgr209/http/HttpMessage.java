package no.kristiania.pgr209.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    public Map<String, String> headers;

    public HttpMessage(InputStream inputStream) {

    }

    public static String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            line.append((char)c);
        }
        c = socket.getInputStream().read(); // read next \n character
        if (c != '\n') {
            throw new IllegalStateException("Invalid http header - \\r not followed by \\n");
        }
        return line.toString();
    }

    public static Map<String, String> readHeaders(Socket socket) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()) {
            String[] headerParts = headerLine.split("\s*:\s*", 2);
            headers.put(headerParts[0], headerParts[1]);
        }
        return headers;
    }
}
