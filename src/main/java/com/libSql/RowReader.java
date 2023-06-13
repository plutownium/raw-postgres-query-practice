package com.libSql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class RowReader {
    public void printValuableText(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        System.out.println("querying SELECT * FROM whatever");
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }

    public Integer getId(ResultSet rs) throws SQLException {
        rs.next();
        String theId = rs.getString(1);
        Integer id = Integer.parseInt(theId);
        return id;
    }

    public int extractCount(ResultSet rs) throws SQLException {
        // try {

        rs.next();
        int count = rs.getInt(1);
        // rs.close();
        return count;
        // } catch (SQLException e) {
        // System.out.println(e.getErrorCode());
        // System.out.println("Can't count for some reason");
        // return 999999; // fail;
        // }
    }
}