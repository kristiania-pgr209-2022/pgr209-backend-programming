package no.kristiania.library.database;

import java.util.Random;

public class SampleData {

    private static final Random random = new Random();

    static Book sampleBook() {
        var book = new Book();
        book.setTitle(pickOne(
                "The Adventures of ",
                "Becoming ",
                "The start of "
        ) + sampleFullName());
        book.setAuthorName(sampleFullName());
        return book;
    }

    public static Library sampleLibrary() {
        var library = new Library();
        library.setName(
                pickOne("Oslo", "Grunerløkka", "Bergen", "Sogndal", "Ulvik")
                + " " +
                pickOne("Public Library", "Deichmanske", "City Library")
        );
        library.setAddress(sampleFullName() + " gate " + random.nextInt(100));
        return library;
    }

    private static String sampleFullName() {
        return pickOne("Johannes", "Simen", "Magnus")
                + " " +
               pickOne("Johannessen", "Simensen", "Magnussen");
    }

    private static String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }

}
