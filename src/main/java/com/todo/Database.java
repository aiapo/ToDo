package com.todo;

import java.sql.*;
public class Database {
    // ABILITY TO USE DIFFERENT DATABASE FILES
    static String databaseFile;
    public Database(String s) {
        databaseFile = s;
    }

    // CREATE SQLITE DATABASE
    public static boolean createDB(){
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+databaseFile);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        return true;
    }

    // CREATE TABLE IN SQLITE DATABASE
    public static boolean createDBTables(String sql){
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+databaseFile);

            stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        return true;
    }

    // INSERT BASED ON PASSED QUERY, USE WRAPPER
    public static boolean insertDB(String sql){
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+databaseFile);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        return true;
    }

    // SELECT BASED ON PASSED QUERY, USE WRAPPER
    public static ResultSet selectDB(String sql) throws SQLException, ClassNotFoundException {
        Connection c;
        Statement stmt;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
}
