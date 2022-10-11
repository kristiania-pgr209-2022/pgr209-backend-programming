package no.kristiania.library;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/books")
public class BookResource {

    @GET
    public String getAllBooks() {
        return "{\"title\":\"Hello World\"";
    }
}
