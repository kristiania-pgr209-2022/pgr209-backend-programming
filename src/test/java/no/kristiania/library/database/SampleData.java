package no.kristiania.library.database;

import java.util.List;
import java.util.Random;

public class SampleData {

    private static final Random random = new Random();

    static Library sampleLibrary() {
        var library = new Library();
        library.setName(pickOne(List.of(
                "Oslo ", "Grunerløkka ", "Bergen ", "Trondheim "
        )) + " " + pickOne(List.of("Deichmanske bibliotek", "public library", "city library")));
        library.setAddress(sampleFirstName() +  " " + sampleLastName() + " street");
        return library;
    }

    static Book sampleBook() {
        Book book = new Book();
        book.setTitle(pickOne(List.of(
                "The trials of ", "The origin of ", "Much ado about ", "Getting to know "
        )));
        book.setAuthor(sampleFirstName() + " " + sampleLastName());
        return book;
    }

    private static String sampleLastName() {
        return pickOne(List.of("Olsen", "Jensen", "Hansen", "Johannessen", "Magnussen", "Simensen"));
    }

    private static String sampleFirstName() {
        return pickOne(List.of("Johannes", "Magnus", "Simen", "Ole", "Dole", "Doffen"));
    }

    private static String pickOne(List<String> alternatives) {
        return alternatives.get(random.nextInt(alternatives.size()));
    }
}
