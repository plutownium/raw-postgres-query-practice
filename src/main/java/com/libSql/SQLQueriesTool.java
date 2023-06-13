package com.libSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;




public class SQLQueriesTool {
    private final String url = "jdbc:postgresql://localhost:5432/";
    private final String dbName = "libraryapp"; // note lowercase
    private final String username = "postgres";
    private final String password = "postgres";

    private Connection connection;

    private Statement statement;

    private static final String DATABASE_DRIVER = "org.postgresql.Driver";

    private PrettyPrinter printer;

    public SQLQueriesTool() {
        this.printer = new PrettyPrinter();
    }


    public Connection connect() {
        boolean noConnectionYet = connection == null;
        if (noConnectionYet)
        {
            try {
                Class.forName(DATABASE_DRIVER);
                System.out.println("library app db connection established");
                connection = (Connection) DriverManager.getConnection(url + dbName, username, password);
                // Statement stmt = connection.createStatement();
                // String sql = "CREATE DATABASE " + dbName;
                // stmt.executeUpdate(sql);
                // System.out.println("Database created successfully...");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("PROBLEM\n########");
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.out.println("########\n");
            }
        }
        return connection;
    }


    public void disconnect()
    {
        boolean establishedConnection = connection != null;
        if (establishedConnection)
        {
            try
            {
                connection.close();

                connection = null;

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public ResultSet operate(String query) throws SQLException {
        statement = connection.createStatement();
        System.out.println("Executing: " + query);
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println("Here is resultset:");
        System.out.println(resultSet);
        return resultSet;
    }

    public Integer operateUpdate(String query) throws SQLException {
        // https://stackoverflow.com/questions/21276059/no-results-returned-by-the-query-error-in-postgresql
        // Use executeUpdate instead of executeQuery if no data will be returned (i.e. a non-SELECT operation).
        statement = connection.createStatement();
        this.printer.prettyYellow("Executing update: " + query);
        Integer thing = statement.executeUpdate(query);

        return thing;
    }
}