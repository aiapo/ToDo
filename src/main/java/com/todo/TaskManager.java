package com.todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TaskManager {
    //public TaskDatabase tDB;
    TaskDatabase tDB = new TaskDatabase();

    public TaskManager() throws SQLException {
        tDB.init();
    }

    public void add(String name, String description, String creation, String due, Integer complete){
        Object[] newTask = {0, name, description, creation, due, complete};
        if(tDB.insert("Tasks",newTask)){
            System.out.println("Task "+name+" created!");
        }else{
            System.out.println("Error creating "+name+"!");
        }

    }

    public void get() throws SQLException {
        String[] attributes = {"name", "description"};
        Object[] is = {"0"};
        ResultSet rs = tDB.select("Tasks", attributes, "completion = ?", is);
        while(rs.next()) {
            System.out.println(rs.getString("name") + ": " + rs.getString("description"));
        }
    }

}
