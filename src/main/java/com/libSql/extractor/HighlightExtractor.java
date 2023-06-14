package com.libSql.extractor;

import com.libSql.objects.Highlight;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class HighlightExtractor {

    public static Highlight[] extract(ResultSet rs) throws SQLException {
        ArrayList<Highlight> found = new ArrayList<Highlight>();
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnCount = rsmd.getColumnCount();
        System.out.println("Starting book extractor");
        while (rs.next()) {
            String highlightText = "";
            Integer highlightId = 0;
            Integer guestId = 0;
            Integer bookId = 0;
            for (int i = 1; i <= columnCount; i++) {
                String columnValue = rs.getString(i);
                String colName = rsmd.getColumnName(i);
                if (colName == "highlight") {
                    highlightText = columnValue;
                } else if (colName == "guest_id") {
                    guestId = Integer.parseInt(columnValue);
                } else if (colName == "book_id") {
                    bookId = Integer.parseInt(columnValue);
                } else if (colName == "id") {
                    highlightId = Integer.parseInt(columnValue);
                }

            }
            Highlight newHighlight = new Highlight(highlightId, highlightText, guestId, bookId);
            found.add(newHighlight);
        }

        // turn the arraylist into an array so I don't have to import arrayList everywhere.
        Highlight[] foundHighlights = new Highlight[found.size()];
        return foundHighlights;
    }
}
