create table physical_books
(
    id         int identity primary key,
    library_id integer references libraries (id) not null,
    book_id    integer references books (id)     not null
);
