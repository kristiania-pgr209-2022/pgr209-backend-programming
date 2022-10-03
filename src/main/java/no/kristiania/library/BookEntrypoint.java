package no.kristiania.library;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class BookEntrypoint {

    private static final List<Book> books = new ArrayList<>();

    @GET
    @Path("/books")
    public Response getBooks() {
        JsonArrayBuilder booksJson = Json.createArrayBuilder();
        for (Book book : books) {
            booksJson.add(Json.createObjectBuilder()
                    .add("title", book.getTitle())
            );
        }
        return Response.ok(booksJson.build().toString(), "application/json").build();
    }

    @POST
    @Path("/books")
    public Response addBook(String body) {
        JsonObject object = Json.createReader(new StringReader(body)).readObject();
        var book = new Book();
        book.setAuthor(object.getString("author"));
        book.setTitle(object.getString("title"));
        books.add(book);
        return Response.ok().build();
    }
}
