package com.libSql.sqlMaker;

public class HighlightSQLMaker {
    /* So, there's one row in the highlights table per highlight per book. */
    public String createHighlightTableIfNotExists()  {
        return String.format("CREATE TABLE IF NOT EXISTS Highlights " +
                "(ID                     SERIAL PRIMARY KEY," +
                " HIGHLIGHT              TEXT       NOT NULL, " +
                " GUEST_ID               INT        NOT NULL," +
                " FOREIGN KEY (guest_id) REFERENCES Guests(id), " +
                " BOOK_ID                INT        NOT NULL, " +
                " FOREIGN KEY (book_id)  REFERENCES Books(id)" +
                ");");
    }

    public String addHighlight(Integer guestId, Integer bookId, String text) {
        return String.format("INSERT INTO Highlights (highlight, guest_id, book_id) " +
                " VALUES ('%s', %s, %s) RETURNING id;", text, guestId, bookId);
    }

    public String getAllForGuest(Integer guestId) {
        return String.format("SELECT * FROM Highlights WHERE guest_id = %s;", guestId);
    }

    public String count() {
        return "SELECT count(*) AS exact_count FROM Highlights;";
    }

    public String countChars() {
        return "SELECT SUM(length(highlight)) AS total_characters " +
                "FROM Highlights;";
    }

    public String deleteAll() {
        return "TRUNCATE Highlights CASCADE;";
    }
}
