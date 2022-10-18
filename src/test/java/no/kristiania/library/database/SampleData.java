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
        return book;
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
