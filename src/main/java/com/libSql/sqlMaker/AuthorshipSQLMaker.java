package com.libSql.sqlMaker;

public class AuthorshipSQLMaker {
    public String createAuthorshipTableIfNotExists()  {
        return String.format("CREATE TABLE IF NOT EXISTS Authorships " +
                "(ID                     SERIAL PRIMARY KEY," +
                " AUTHOR_ID              INT     NOT NULL, " +
                " BOOK_ID                INT     NOT NULL, " +
                " FOREIGN KEY (author_id) REFERENCES Authors(id) ON DELETE CASCADE, " +
                " FOREIGN KEY (book_id)   REFERENCES Books(id)   ON DELETE CASCADE);");
    }

    public String createAuthorship(Integer authorId, Integer bookId) {
        return String.format("INSERT INTO Authorships (author_id, book_id) VALUES (%s, %s);", authorId, bookId);
    }

    public String linkBook(Integer authorId, Integer bookId) {
        return this.createAuthorship(authorId, bookId);
    }

    public String getBooksForAuthor(Integer authorId) {
        return String.format("SELECT * FROM Authorships WHERE author_id=%s;", authorId);
    }


    public String count() {
        return "SELECT count(*) AS exact_count FROM Authorships;";
    }




    public String deleteAll() {
        return "TRUNCATE Authorships;";
    }
}
