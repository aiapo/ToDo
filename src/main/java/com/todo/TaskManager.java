package com.todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TaskManager {
    TaskDatabase tDB = new TaskDatabase();

    public TaskManager() throws SQLException {
        tDB.init();
    }

    public void add(String name, String description, String creation, String due, Integer complete){
        Object[] newTask = {null, name, description, creation, due, complete};
        if(tDB.insert("Tasks",newTask)){
            System.out.println("Task "+name+" created!");
        }else{
            System.out.println("Error creating "+name+"!");
        }

    }

    public ResultSet get(String table, String[] attributes) throws SQLException {
        return tDB.select(table, attributes);
    }
    public ResultSet get(String table, String[] attributes, String condition, Object[] params) throws SQLException {
        return tDB.select(table, attributes, condition, params);
    }

}
