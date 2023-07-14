package com.todo;

import java.io.File;
import java.sql.SQLException;

public class TaskDatabase {
    // For this project we are using SQLite, just call our database file tasks.db
    static String databaseName = "tasks.db";

    // Create the database/connect to it
    static Database database;
    static {
        try {
            database = new Database("tasks.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Initialize the database if it hasn't been already (create tables)
    public static void init(){
        if (new File(databaseName).length() <= 0) {
                String newTable = "Tasks";
                Object[] newAttributes = {
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT",
                        "NAME   TEXT    NOT NULL",
                        "DESCRIPTION    TEXT    NOT NULL",
                        "CREATION_DATE  TEXT    NOT NULL",
                        "DUE_DATE   TEXT    NOT NULL",
                        "COMPLETION INTEGER NOT NULL"
                };
                createTable(newTable,newAttributes);
        }
    }

    // Table creator
    private static void createTable(String tableName,Object[] attributes){
        try{
            int response = database.create(tableName, attributes);
            if(response==0)
                System.out.println("Created table "+tableName);
            else
                System.out.println("Table creation failed for "+tableName);
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
        }
    }

}