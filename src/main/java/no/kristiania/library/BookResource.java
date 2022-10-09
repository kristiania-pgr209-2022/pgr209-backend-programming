package no.kristiania.library;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("")
public class BookResource {
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public String listBooks() {
        return "[{\"title\":\"Hello World\"";
    }
}
