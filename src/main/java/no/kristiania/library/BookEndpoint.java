package no.kristiania.library;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BookEndpoint {
    private static final List<Book> books = new ArrayList<>();
    static {
        var exampleBook = new Book();
        exampleBook.setTitle("Java in a nutshell");
        exampleBook.setAuthor("David Flannagan");
        books.add(exampleBook);
    }

    @Path("/")
    @GET
    public Response getAllBooks() {
        var result = Json.createArrayBuilder();
        for (var book : books) {
            result.add(Json.createObjectBuilder()
                    .add("title", book.getTitle())
                    .add("author", book.getAuthor())
            );
        }
        return Response.ok(result.build().toString()).build();
    }

    @Path("/")
    @POST
    public Response addBook(String body) {
        JsonObject jsonBook = Json.createReader(new StringReader(body)).readObject();
        var book = new Book();
        book.setAuthor(jsonBook.getString("author"));
        book.setTitle(jsonBook.getString("title"));
        books.add(book);

        return Response.ok().build();
    }
}
