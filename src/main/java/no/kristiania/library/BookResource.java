package no.kristiania.library;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("")
public class BookResource {
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World";
    }
}
