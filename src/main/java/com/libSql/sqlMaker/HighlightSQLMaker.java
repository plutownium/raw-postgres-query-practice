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

    public String count() {
        return "SELECT count(*) AS exact_count FROM Highlights;";
    }

    public String deleteAll() {
        return "TRUNCATE Highlights CASCADE;";
    }
}
