package no.kristiania.library;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/books")
public class BookEndpoint {
    @POST
    public void addBook() {

    }
}
