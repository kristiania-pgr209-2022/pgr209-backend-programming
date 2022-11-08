package no.kristiania;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/books")
public class BookEndpoint {

    @POST
    public void addBook() {

    }

    @GET
    public String listBooks() {
        return "David Flanagan";
    }
}
