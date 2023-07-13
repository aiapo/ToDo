package com.todo;

import java.sql.*;
public class Database {
    static String databaseFile;
    public Database(String s) {
        databaseFile = s;
    }

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

    public static boolean createDBTables(){
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+databaseFile);

            stmt = c.createStatement();
            String sql = "CREATE TABLE TASKS " +
                    "(ID             INTEGER     PRIMARY KEY    AUTOINCREMENT," +
                    " NAME           TEXT    NOT NULL, " +
                    " CREATION_DATE  TEXT    NOT NULL, " +
                    " DUE_DATE       TEXT    NOT NULL," +
                    " COMPLETION     INTEGER    NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        return true;
    }

    public static boolean insertDBTuples(String insertName, String createDate, String dueDate, Integer completeStatus){
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+databaseFile);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO TASKS (NAME,CREATION_DATE,DUE_DATE,COMPLETION) " +
                    "VALUES ('"+insertName+"','"+createDate+"','"+dueDate+"',"+completeStatus+");";
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
}
