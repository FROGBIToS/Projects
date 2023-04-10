
ALTER TABLE Author_Book
    DROP FOREIGN KEY Author_Book_Author;

ALTER TABLE Author_Book
    DROP FOREIGN KEY Author_Book_Book;

-- tables
DROP TABLE Author;

DROP TABLE Author_Book;

DROP TABLE Book;

-- End of file.

