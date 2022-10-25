package no.kristiania.library.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "physical_books")
public class PhysicalBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Library library;

    private Book book;

    public PhysicalBook() {
    }

    public PhysicalBook(Library library, Book book) {
        this.library = library;
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
