package com.libSql.objects;

    import java.sql.SQLException;

import com.libSql.SQLQueriesTool;
import com.libSql.sqlMaker.AuthorshipSQLMaker;
import com.libSql.sqlMaker.BookSQLMaker;

import java.sql.ResultSet;

public class Book {
    private ResultSet book;
    private SQLQueriesTool pg;
    //
    private Integer id;
    private String name;
    private Integer authorId;
    private Integer year;
    private Boolean rented;

    public Book(SQLQueriesTool pg, String name, Integer authorId, Integer published) throws SQLException {
        this.name = name;
        this.authorId = authorId;
        this.year = published;
        BookSQLMaker bookTool = new BookSQLMaker();
        String query = bookTool.createBook(name, published);
        System.out.println("=========");
        System.out.println(query);
        ResultSet book = pg.operate(query);
        this.setBook(book);
        this.setPg(pg);
        // establish link
        AuthorshipSQLMaker authorshipTool = new AuthorshipSQLMaker();
        String linkingQuery = authorshipTool.createAuthorship(authorId, this.getId());
        System.out.println("Linking query:" + linkingQuery);
        pg.operateUpdate(linkingQuery);
    }

    // for creating books that already exist in the db
    public Book(Integer id, String name, Integer published, boolean rented) throws SQLException {
        this.id = id;
        this.name = name;
        this.year = published;
        this.rented = rented;
    }

    private void setBook(ResultSet book) {
        this.book = book;
    }

    private void setPg(SQLQueriesTool pg) {
        this.pg = pg;
    }

    private SQLQueriesTool getPg() {
        return this.pg;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() throws SQLException {
        if (this.id != null) {
            return this.id;
        }
        this.book.next();
        String theId = this.book.getString(1);
        Integer id = Integer.parseInt(theId);
        this.setId(id);
        return id;
    }

    public void addAuthor(Integer authorId) throws SQLException {
        AuthorshipSQLMaker authorshipTool = new AuthorshipSQLMaker();
        String linkingQuery = authorshipTool.createAuthorship(authorId, this.getId());
        SQLQueriesTool pg = this.getPg();
        pg.operateUpdate(linkingQuery);
        BookSQLMaker bookTool = new BookSQLMaker();
        ResultSet linked = pg.operate(bookTool.getBooksForAuthor(authorId));
        this.setBook(linked);

    }
}
