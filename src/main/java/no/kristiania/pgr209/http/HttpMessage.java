package no.kristiania.pgr209.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    private final String startLine;
    public Map<String, String> headers;
    private final String body;

    public HttpMessage(InputStream inputStream) throws IOException {
        startLine = HttpMessage.readLine(inputStream);
        headers = HttpMessage.readHeaders(inputStream);

        StringBuilder body = new StringBuilder();
        for (int i = 0; i < getContentLength(); i++) {
            body.append((char) inputStream.read());
        }
        this.body = body.toString();
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    private String getHeader(String name) {
        return headers.get(name);
    }


    public String getStartLine() {
        return startLine;
    }

    public static String readLine(InputStream inputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != '\r') {
            line.append((char)c);
        }
        c = inputStream.read(); // read next \n character
        if (c != '\n') {
            throw new IllegalStateException("Invalid http header - \\r not followed by \\n");
        }
        return line.toString();
    }

    public static Map<String, String> readHeaders(InputStream inputStream) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        String headerLine;
        while(!(headerLine = readLine(inputStream)).isEmpty()) {
            String[] headerParts = headerLine.split("\s*:\s*", 2);
            headers.put(headerParts[0], headerParts[1]);
        }
        return headers;
    }

    public String getBody() {
        return body;
    }
}
