package com.todo;

import java.sql.SQLException;

public class Manager {
    TaskDatabase DB = new TaskDatabase();

    // Create a new TaskManager instance
    TaskManager Tasks = new TaskManager(DB);
    // Create a new CategoryManager instance
    CategoryManager Categories = new CategoryManager(DB);

    public Manager() throws SQLException {
        DB.init();
    }


}
