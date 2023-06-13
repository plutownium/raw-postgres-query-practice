package com.libSql.sqlMaker;

import java.sql.SQLException;


public class AuthorSQLMaker {
    public String createAuthorTableIfNotExists() {
        return String.format("CREATE TABLE IF NOT EXISTS Authors " +
                "(ID                     SERIAL PRIMARY KEY," +
                " FIRSTNAME              TEXT    NOT NULL, " +
                " AGE                    INT     NOT NULL);");
    }

    public String createAuthor(String firstName, Integer age) {
        return String.format("INSERT INTO Authors (firstname, age) " +
                "VALUES ('%s', '%s') RETURNING id;", firstName, age);
    }

    public String count() {
        return "SELECT count(*) AS exact_count FROM Authors;";
    }

    public String getAllAuthors() throws SQLException {
        return "SELECT * FROM Authors";
    }

    public String deleteAuthorByName(String name) {
        return String.format("DELETE FROM Authors WHERE firstname=%s;", name);
    }


    public String deleteAll() {
        return "TRUNCATE Authors CASCADE;";
    }
}
