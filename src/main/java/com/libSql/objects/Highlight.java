package com.libSql.objects;

import java.sql.SQLException;

public class Highlight {
//new Highlight(highlightId, highlightText, guestId, bookId);

    private Integer id;
    private String text;
    private Integer guestId;
    private Integer bookId;
// for creating highlights that already exist in the db
    public Highlight(Integer id, String highlight, Integer guestId, Integer bookId) {
        this.id = id;
        this.text = highlight;
        this.guestId = guestId;
        this.bookId = bookId;
    }

    public String toString() {
        return String.format("Highlight {id: %s, text: %s, guestId: %s, bookId: %s }",
                id, text, guestId, bookId);
    }
}
