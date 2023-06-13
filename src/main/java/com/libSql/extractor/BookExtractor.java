package com.libSql.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import com.libSql.objects.Book;

public class BookExtractor {

    public static Book[] extract(ResultSet rs) throws SQLException {
        ArrayList<Book> found = new ArrayList<Book>();
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnCount = rsmd.getColumnCount();
        System.out.println("Starting book extractor");
        while (rs.next()) {
            String title = "";
            Integer year = 0;
            Integer id = 0;
            Boolean rented = null;
            for (int i = 1; i <= columnCount; i++) {
                String columnValue = rs.getString(i);
                String colName = rsmd.getColumnName(i);
                if (colName == "title") {
                    title = columnValue;
                } else if (colName == "year") {
                    year = Integer.parseInt(columnValue);
                } else if (colName == "id") {
                    id = Integer.parseInt(columnValue);
                } else if (colName == "rented") {
                    rented = Boolean.parseBoolean(columnValue);
                }
                // goal: fin dout what this prints
            }
            Book newBook = new Book(id, title, year, rented);
            found.add(newBook);
        }

        // turn the arraylist into an array so I don't have to import arrayList everywhere.
        Book[] foundBooks = new Book[found.size()];
        return foundBooks;
    }
}
