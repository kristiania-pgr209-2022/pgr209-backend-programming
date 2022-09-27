package no.kristiania.library;

public class Book {
    private String author;
    private String title;

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book{" +
               "author='" + author + '\'' +
               ", title='" + title + '\'' +
               '}';
    }
}
