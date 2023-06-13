package com.libSql.sqlMaker;

import java.sql.SQLException;

public class GuestSQLMaker {
    public String createGuestTableIfNotExists()  {
        return String.format("CREATE TABLE IF NOT EXISTS Guests " +
                "(ID                     SERIAL PRIMARY KEY," +
                " NAME                   TEXT    NOT NULL, " +
                " TOTAL_CHECKOUTS        INT     NOT NULL, " +
                " CURRENTLY_BORROWED     INT     NULL," +
                " FOREIGN KEY (currently_borrowed) REFERENCES Books(id)," +
                " FAVORITE_AUTHOR        INT     NULL," + // they can have a favorite author, or not
                " FOREIGN KEY (favorite_author) REFERENCES Authors(id)" +
                ");");
    }

    public String createGuest(String name, Integer startingCheckouts) {
        return String.format("INSERT INTO Guests (name, total_checkouts) " +
                "VALUES ('%s', %s) RETURNING id;", name, startingCheckouts);
    }

    public String addFavoriteAuthor(Integer guestId, Integer authorId) {
        return String.format("UPDATE Guests SET favorite_author = %s WHERE id = %s;", guestId, authorId);
    }

    public String count() {
        return "SELECT count(*) AS exact_count FROM Guests;";
    }

    public String rentBookToGuest(Integer bookId, Integer guestId) {
        return String.format("UPDATE Guests SET currently_borrowed = %s WHERE id = %s;", bookId, guestId);
    }

    public String deleteAll() {
        return "TRUNCATE Guests CASCADE;";
    }
}
