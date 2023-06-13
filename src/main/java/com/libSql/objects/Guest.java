package com.libSql.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.libSql.SQLQueriesTool;
import com.libSql.sqlMaker.BookSQLMaker;
import com.libSql.sqlMaker.GuestSQLMaker;
import com.libSql.sqlMaker.HighlightSQLMaker;



public class Guest {

    private ResultSet guest;
    private SQLQueriesTool pg;

    private Integer id;
    private String name;
    private Integer checkouts;
    private Integer borrowedBookId;
    private Integer favoriteAuthorId;


    public Guest(SQLQueriesTool pg, String name) throws SQLException {
        GuestSQLMaker guestTool = new GuestSQLMaker();

        Integer startingCheckouts = 0;
        String query = guestTool.createGuest(name, startingCheckouts);
        System.out.println(query);
        System.out.println(query);
        System.out.println(query);
        System.out.println(query);
        System.out.println(query);
        ResultSet guest = pg.operate(query);
        this.setGuest(guest);
        this.setPg(pg);
    }

    public Guest addFavoriteAuthor(Integer authorId) throws SQLException {
        GuestSQLMaker guestTool = new GuestSQLMaker();
        String query = guestTool.addFavoriteAuthor(this.getId(), authorId);
        this.getPg().operateUpdate(query);
        return this;
    }

    public void rentBook(Book book) throws SQLException {
        BookSQLMaker bookTool = new BookSQLMaker();
        String markRentedQuery = bookTool.rentBookToGuest(book.getId()); // mark book rented
        this.getPg().operateUpdate(markRentedQuery);
        //
        GuestSQLMaker guestTool = new GuestSQLMaker();
        String updateGuestQuery = guestTool.rentBookToGuest(book.getId(), this.getId());
        this.getPg().operateUpdate(updateGuestQuery);
        //
        this.setBorrowedBookId(book.getId());
    }

    private void setGuest(ResultSet guest) {
        this.guest = guest;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    private void setPg(SQLQueriesTool pg) {
        this.pg = pg;
    }

    private void setBorrowedBookId(Integer id) {
        this.borrowedBookId = id;
    }

    private SQLQueriesTool getPg() {
        return this.pg;
    }

    public Integer getId() throws SQLException {
        if (this.id != null) {
            return this.id;
        }
        this.guest.next();
        String stringId = this.guest.getString(1);
        Integer id = Integer.parseInt(stringId);
        this.setId(id);
        return id;
    }

    public void addHighlight(String highlight) {
        // only works if the guest has a book! or else how can they add the highlight?
        boolean hasRentedBook = this.borrowedBookId != null;
        if (hasRentedBook) {
            HighlightSQLMaker highlightTool = new HighlightSQLMaker();
        }
    }
}