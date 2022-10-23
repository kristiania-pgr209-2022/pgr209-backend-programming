create table physical_books
(
    id    int identity primary key,
    library_id int references libraries (id) not null,
    book_id    int references books (id)     not null
);
