package no.kristiania.library;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")
public class BookEndpoint {
    @Path("/")
    @GET
    public Response getAllBooks() {
        var exampleBook = new Book();
        exampleBook.setTitle("Java in a nutshell");
        exampleBook.setAuthor("David Flannagan");
        var books = List.of(exampleBook);

        JsonArrayBuilder result = Json.createArrayBuilder();
        for (var book : books) {
            result.add(Json.createObjectBuilder()
                    .add("title", book.getTitle())
                    .add("author", book.getAuthor())
            );
        }

        return Response.ok(result.build().toString()).build();
    }
}
