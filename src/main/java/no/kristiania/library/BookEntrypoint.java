package no.kristiania.library;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class BookEntrypoint {

    @GET
    @Path("/books")
    public Response getBooks() {
        var exampleBook = new Book();
        exampleBook.setAuthor("Test Persson");
        exampleBook.setTitle("Example Book");
        var books = List.of(exampleBook);
        JsonArrayBuilder booksJson = Json.createArrayBuilder();
        for (Book book : books) {
            booksJson.add(Json.createObjectBuilder()
                    .add("title", book.getTitle())
            );
        }

        return Response.ok(booksJson.build().toString(), "application/json").build();
    }
}
