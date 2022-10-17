package no.kristiania.library.database;

public class SampleData {
    static Library sampleLibrary() {
        var library = new Library();
        library.setName("Deichmanske");
        library.setAddress("Dronning Eufemias gate");
        return library;
    }

    static Book sampleBook() {
        Book book = new Book();
        book.setTitle("Sample title");
        book.setAuthor("Firstname Lastname");
        return book;
    }
}
