package com.libSql;


import com.libSql.sqlMaker.*;

import java.sql.ResultSet;
import java.sql.SQLException;

class StartupInitializer {
    public static void initialize(SQLQueriesTool pg, BookSQLMaker bookTool, AuthorSQLMaker authorTool, AuthorshipSQLMaker authorshipsTool, GuestSQLMaker guestsTool, HighlightSQLMaker highlightsTool) {
        String lastQuery = "";
        PrettyPrinter printer = new PrettyPrinter();

        try {
            String makeAuthorTableQuery = authorTool.createAuthorTableIfNotExists();
            String makeBookTableQuery = bookTool.createBookTableIfNotExists();
            String makeAuthorshipsTableQuery = authorshipsTool.createAuthorshipTableIfNotExists();
            String makeGuestsTableQuery = guestsTool.createGuestTableIfNotExists();
            String makeHighlightsTableQuery = highlightsTool.createHighlightTableIfNotExists();

            lastQuery = makeBookTableQuery;
            pg.operateUpdate(makeAuthorTableQuery);
            lastQuery = makeAuthorTableQuery;
            pg.operateUpdate(makeBookTableQuery);
            lastQuery = makeAuthorshipsTableQuery;
            pg.operateUpdate(makeAuthorshipsTableQuery);
            lastQuery = makeGuestsTableQuery;
            pg.operateUpdate(makeGuestsTableQuery);
            lastQuery = makeHighlightsTableQuery;
            pg.operateUpdate(makeHighlightsTableQuery);
            // count rows
            RowReader reader = new RowReader();
            String c1 = bookTool.count();
            String c2 = authorTool.count();
            String c3 = authorshipsTool.count();
            String c4 = guestsTool.count();
            String c5 = highlightsTool.count();

            lastQuery = c1;
            ResultSet booksCountQuery = pg.operate(c1);
            ResultSet authorsCountQuery = pg.operate(c2);
            ResultSet authorshipsCountQuery = pg.operate(c3);
            ResultSet guestsCountQuery = pg.operate(c4);
            ResultSet highlightsCountQuery = pg.operate(c5);
            Integer booksCount = reader.extractCount(booksCountQuery);
            Integer authorsCount = reader.extractCount(authorsCountQuery);
            Integer authorshipsCount = reader.extractCount(authorshipsCountQuery);
            Integer guestsCount = reader.extractCount(guestsCountQuery);
            Integer highlightsCount = reader.extractCount(highlightsCountQuery);
            //
            //
            // System.out.println(booksCount);
            // System.out.println(authorsCount);
            // if (booksCount > 0) {
            String deleteAllBooks = bookTool.deleteAll();
            lastQuery = deleteAllBooks;
            pg.operateUpdate(deleteAllBooks);
            // System.exit(0);
            // } else {
            // System.out.println("No books...");
            // }
            // if (authorsCount > 0) {
            String deleteAllAuthors = authorTool.deleteAll();
            lastQuery = deleteAllAuthors;
            pg.operateUpdate(deleteAllAuthors);
            String deleteAllLinks = authorshipsTool.deleteAll();
            lastQuery = deleteAllLinks;
            pg.operateUpdate(deleteAllLinks);
            // } else {
            // System.out.println("No authors...");
            // }
            pg.operateUpdate(guestsTool.deleteAll());
            pg.operateUpdate(highlightsTool.deleteAll());
        } catch (SQLException e) {
            printer.prettyRed("initializing error.");
            printer.prettyYellow(lastQuery);
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("\nerror over\n==========\n");
        }
    }
}

