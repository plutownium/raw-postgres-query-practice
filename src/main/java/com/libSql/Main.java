package com.libSql;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.libSql.objects.Author;
import com.libSql.objects.Book;
import com.libSql.objects.Guest;
import com.libSql.sqlMaker.AuthorSQLMaker;
import com.libSql.sqlMaker.AuthorshipSQLMaker;
import com.libSql.sqlMaker.BookSQLMaker;
import com.libSql.sqlMaker.GuestSQLMaker;
import com.libSql.sqlMaker.HighlightSQLMaker;
import com.libSql.extractor.BookExtractor;
import com.libSql.SQLQueriesTool;


public class Main {
    public static void main(String[] args) {
        System.out.println("Started...");
        PrettyPrinter printer = new PrettyPrinter();

        SQLQueriesTool pg = new SQLQueriesTool();
        pg.connect();

        RowReader reader = new RowReader();
        BookSQLMaker bookTool = new BookSQLMaker();
        AuthorSQLMaker authorTool = new AuthorSQLMaker();
        AuthorshipSQLMaker authorshipsTool = new AuthorshipSQLMaker();
        GuestSQLMaker guestsTool = new GuestSQLMaker();
        HighlightSQLMaker highlightsTool = new HighlightSQLMaker();
        StartupInitializer.initialize(pg, bookTool, authorTool, authorshipsTool, guestsTool, highlightsTool);
        // let's make 3 authors, 1, 2 and 3 books each

        String mostRecentQuery = "";
        try {
            Integer alexanderId = new Author(pg, "Alexander", 33).getId();
            Integer perryId = new Author(pg, "Perry", 18).getId();
            Integer robertId = new Author(pg, "Robert", 88).getId();
            int brutusId = new Author(pg, "Brutus", 45).getId();
            Integer sarahId = new Author(pg, "Sarah", 24).getId();
            // books
            new Book(pg, "Foo", alexanderId, 2003);
            new Book(pg, "Bar", alexanderId, 1993);
            new Book(pg, "Baz", alexanderId, 2009);
            //
            new Book(pg, "Cat", perryId, 1888);
            new Book(pg, "Hat", perryId, 1889);
            //
            new Book(pg, "Jazz", robertId, 2021);
            //
            new Book(pg, "Joy, New Cities", brutusId, 1983).addAuthor(alexanderId);
            new Book(pg, "Bunjee Jumping History", brutusId, 2002).addAuthor(alexanderId);
            new Book(pg, "Temples Around The World", brutusId, 1989).addAuthor(perryId);
            new Book(pg, "Ham In Western India", brutusId, 2003).addAuthor(robertId);
            new Book(pg, "From China to Britain", brutusId, 2009).addAuthor(sarahId);
            new Book(pg, "The Six Seas", brutusId, 1783).addAuthor(sarahId);

            // // get all authors
            String allAuthors = authorTool.getAllAuthors();
            mostRecentQuery = allAuthors;
            // ResultSet allAuthors2 = pg.operate(allAuthors);
            // // get all books
            String allBooksQuery = bookTool.getAllBooks();
            mostRecentQuery = allBooksQuery;
            ResultSet allBooks = pg.operate(allBooksQuery);
            // reader.printValuableText(allAuthors2);
            // reader.printValuableText(allBooks2);
            String booksForAuthor = bookTool.getBooksForAuthorWithBookDetails(alexanderId);
            mostRecentQuery = booksForAuthor;
            ResultSet alexBooks = pg.operate(booksForAuthor);
            System.out.println("here are alex's books");
            reader.printValuableText(alexBooks); // Expect Alexander to have 5 books!
            String booksForSarah = bookTool.getBooksForAuthorWithBookDetails(sarahId);
            mostRecentQuery = booksForSarah;
            ResultSet sarahsBooks = pg.operate(booksForSarah);
            reader.printValuableText(sarahsBooks); // works - sarah has 2 books

            // Make Authors and Books Many-to-Many - DONE
            Guest catherine = new Guest(pg, "Catherine");
            Guest stephanie = new Guest(pg, "Stephanie");
            // make Guests have a favorite author - Done
            Guest jared = new Guest(pg, "Jared").addFavoriteAuthor(brutusId);
            Guest frodo = new Guest(pg, "Frodo").addFavoriteAuthor(alexanderId);
            Guest ferry = new Guest(pg, "Ferry").addFavoriteAuthor(alexanderId);
            Guest jeremiah = new Guest(pg, "Jeremiah").addFavoriteAuthor(perryId);
            // make Guests have Books, as in guests of the library.
            // add guests, and distribute books amongst the guests.
            Book[] availableBooks = BookExtractor.extract(allBooks);
            Guest[] guests = new Guest[6];
            guests[0] = catherine;
            guests[1] = stephanie;
            guests[2] = jared;
            guests[3] = frodo;
            guests[4] = ferry;
            guests[5] = jeremiah;
            int j = 0;
            for (int i = 0; i < availableBooks.length; i++) {
                Book toAssign = availableBooks[i];
                Guest guestRentingBook = guests[j];
                j++;
                guestRentingBook.rentBook(toAssign);
                if (j > guests.length) {
                    break;
                }
            }
            // make Books have Highlights, and Highlights have Books and Users
            // add 15 highlights among 5 books among 8 guests.
            catherine.addHighlight("a sentence!");
            catherine.addHighlight("Another sentence!");
            catherine.addHighlight("A third sentence!");
            catherine.addHighlight("Yet another sentence!");
            catherine.addHighlight("This is my fifth sentence");
            //
            stephanie.addHighlight("foo foo foo foo foo@@@@");
            stephanie.addHighlight("bar bar bar bar bar@@@@");
            stephanie.addHighlight("baz baz baz baz baz@@@@");
            //
            jared.addHighlight("Cats eating spaghetti!!!!!!");
            jared.addHighlight("Cats climbing trees!!!!!!!!!!!!");
            frodo.addHighlight("Dogs chasing cars...");
            ferry.addHighlight("The sequence of events is suspicious...");
            // Jeremiah has no highlights.

            // retrieve all highlights for a guest.
            Highlight[] highlightsForCatherine = catherine.getHighlights();
            Highlight[] highlightsForSteph = stephanie.getHighlights();
            reader.printValuableText(highlightsForCatherine);
            reader.printValuableText(highlightsForSteph);

            // retrieve all books with multiple highlights.
            Book[] withMultipleHighlights = pg.operate(bookTool.getBooksWithMinimumHighlights(2));
            reader.printValuableText(withMultipleHighlights);

            // retrieve all authors with multiple books.
            Author[] withMultipleBooks = pg.operate(authorTool.getAuthorsWithMultipleBooks());
            reader.printValuableText(withMultipleBooks);

            // count the chars in the highlights
            Integer totalCharsInAllHighlights = pg.operate(highlightsTool.countChars());

            // count the # of book published in the 21st century
            String countRecentBooksQuery = pg.operate(bookTool.countBooksAfterYear(1999));



            // get one of the 1 to 1 relations and get the association via the associated id
            // add a many to many relationship
        } catch (SQLException e) {

            printer.prettyRed("\n\nSomething went wrong in the main func.");
            printer.prettyYellow(mostRecentQuery);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        pg.disconnect();
        System.out.println("Disconnected connection.");
    }
}

