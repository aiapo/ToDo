package com.todo;

import java.io.File;
import java.sql.SQLException;

public class Manager {
    // For this project we are using SQLite, just call our database file tasks.db
    static String databaseName = "tasks.db";
    // Create the database/connect to it
    Database DB = new Database("tasks.db");

    // Create a new TaskManager instance
    TaskManager Tasks = new TaskManager(DB);
    // Create a new CategoryManager instance
    CategoryManager Categories = new CategoryManager(DB);

    public Manager() throws SQLException {
        init();
    }

    // Initialize the database if it hasn't been already (create tables)
    protected void init(){
        if (new File(databaseName).length() <= 0) {
            DB.create(
                    "Tasks", new Object[]{
                            "ID INTEGER PRIMARY KEY AUTOINCREMENT",
                            "NAME   TEXT    NOT NULL",
                            "DESCRIPTION    TEXT    NOT NULL",
                            "CREATION_DATE  TEXT    NOT NULL",
                            "DUE_DATE   TEXT    NOT NULL",
                            "COMPLETION INTEGER NOT NULL"});
            DB.create(
                    "Categories", new Object[]{
                            "ID INTEGER PRIMARY KEY AUTOINCREMENT",
                            "NAME   TEXT  NOT NULL",
                            "DESCRIPTION    TEXT    NOT NULL"});
            DB.create(
                    "TaskCategory", new Object[]{
                            "ID INTEGER PRIMARY KEY AUTOINCREMENT",
                            "TASK_ID    INTEGER  NOT NULL",
                            "CATEGORY_ID    INTEGER  NOT NULL"});
        }
    }

}
