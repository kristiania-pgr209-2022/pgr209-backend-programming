create table book_copies
(
    id         serial primary key,
    book_id    integer not null references books (id),
    library_id integer not null references libraries (id)
);
