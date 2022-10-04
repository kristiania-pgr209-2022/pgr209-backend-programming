package no.kristiania.library;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BookEndpoint {
    private List<Book> books = new ArrayList<>();
    {
        var exampleBook = new Book();
        exampleBook.setTitle("Java in a nutshell");
        exampleBook.setAuthor("David Flannagan");
        books.add(exampleBook);
    }

    @Path("/")
    @GET
    public Response getAllBooks() {
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
