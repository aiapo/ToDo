package com.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskManager {
    TaskDatabase tDB = new TaskDatabase();

    ObservableList<Task> items = FXCollections.observableArrayList();

    // Constructor to initialize database
    public TaskManager() throws SQLException {
        tDB.init();
    }

    // Add a task
    // Adds to both a local array (based on Task class) and the DB
    // Returns the Task array
    public ObservableList<Task> add(Integer id, String name, String description, String creation, String due, Integer complete) throws SQLException {
        // add task to database
        if(id == null){
            Object[] newTask = {null, name, description, creation, due, complete};
            if(tDB.insert("Tasks",newTask)){
                System.out.println("Task "+name+" created!");
            }else{
                System.out.println("Error creating "+name+"!");
            }
            id = get("Tasks", new String[]{"ID"},"MAX",null).getInt("max_id");
        }

        // add task to local list array
        items.add(new Task(id,name,description,creation,due,complete));

        // return the task list
        return items;
    }

    // Get from database (no conditions)
    public ResultSet get(String table, String[] attributes) throws SQLException {
        return tDB.select(table, attributes);
    }
    // Get from database
    public ResultSet get(String table, String[] attributes, String condition, Object[] params) throws SQLException {
        return tDB.select(table, attributes, condition, params);
    }

}
