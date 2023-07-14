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
    public static void initTaskDB(){
        if (new File(databaseName).length() <= 0) {
            try {
                String newTable = "Tasks";
                Object[] attributes = {
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT",
                        "NAME   TEXT    NOT NULL",
                        "DESCRIPTION    TEXT    NOT NULL",
                        "CREATION_DATE  TEXT    NOT NULL",
                        "DUE_DATE   TEXT    NOT NULL",
                        "COMPLETION INTEGER NOT NULL"
                };
                int response = database.create(newTable, attributes);
                if(response==0)
                    System.out.println("Created table "+newTable);
                else
                    System.out.println("Table creation failed for "+newTable);
            } catch (SQLException error) {
                System.out.println("Error: "+error.getMessage());
            }
        }
    }

}