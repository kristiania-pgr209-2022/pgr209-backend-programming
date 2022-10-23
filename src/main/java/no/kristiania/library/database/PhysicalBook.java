package no.kristiania.library.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "physical_books")
@NamedQuery(name = "findByLibrary", query = "select b from PhysicalBook b where b.library.id = :libraryId")
public class PhysicalBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Library library;

    public PhysicalBook() {
    }

    public PhysicalBook(Book book, Library library) {
        this.book = book;
        this.library = library;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
