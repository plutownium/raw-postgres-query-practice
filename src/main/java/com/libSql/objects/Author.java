package com.libSql.objects;


import java.sql.SQLException;
import java.sql.ResultSet;

import com.libSql.SQLQueriesTool;
import com.libSql.sqlMaker.AuthorSQLMaker;



public class Author {

    private ResultSet author;
    private Integer id;

    private String name;

    private Integer age;

    public Author(SQLQueriesTool pg, String fn, Integer age) throws SQLException {
        AuthorSQLMaker authorTool = new AuthorSQLMaker();

        String query = authorTool.createAuthor(fn, age);
        ResultSet author = pg.operate(query);
        this.setAuthor(author);
    }

    // for creating authors that already exist in the db
    public Author(Integer id, String name, Integer age)  {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    private void setAuthor(ResultSet author) {
        this.author = author;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() throws SQLException {
        if (this.id != null) {
            return this.id;
        }
        this.author.next();
        String stringId = this.author.getString(1);
        Integer id = Integer.parseInt(stringId);
        this.setId(id);
        return id;
    }

    public String toString() {
        return String.format("Author {name: %s, id: %s }",
                "null!", id);
    }
}