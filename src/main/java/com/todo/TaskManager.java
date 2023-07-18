package com.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskManager {
    TaskDatabase tDB = new TaskDatabase();

    ObservableList<Task> taskItems = FXCollections.observableArrayList();

    // Constructor to initialize database
    public TaskManager() throws SQLException {
        tDB.init();
    }

    // Add a task
    // Adds to both a local array (based on Task class) and the DB
    // Returns the Task array
    public ObservableList<Task> add(Integer id, String name, String description, String creation, String due, Integer complete) {
        try{
            // add task to database
            if(id == null){
                Object[] newTask = {null, name, description, creation, due, complete};
                if(tDB.insert("Tasks",newTask)){
                    System.out.println("Task "+name+" created!");
                }else{
                    System.out.println("Error creating "+name+"!");
                }
                id = tDB.select("Tasks", new String[]{"ID"},"MAX",null).getInt("max_id");
            }

            // add task to local list array
            taskItems.add(new Task(id,name,description,creation,due,complete));

            // return the task list
            return taskItems;
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return taskItems;
        }
    }

    // Retrieve a task as a Task type
    public Task retrieve(Integer id){
        return taskItems.get(id-1);
    }

    public ObservableList<Task> update(Integer id, String name, String description, String creation, String due, Integer complete){
        // update task in database
        String[] task = {"name", "description", "creation_date", "due_date", "completion"};
        if(tDB.update("Tasks",task,"id = "+id,new Object[]{name,description,creation,due,complete})){
            System.out.println("Task "+name+" updated!");
        }else{
            System.out.println("Error updating "+name+"!");
        }

        taskItems.set(id-1,new Task(id,name,description,creation,due,complete));

        // return the task list
        return taskItems;
    }

    public ObservableList<Task> populateArray(){
        try{
            ResultSet allTasks = tDB.select("Tasks", new String[]{"*"});
            while(allTasks.next()) {
                taskItems.add(new Task(allTasks.getInt("ID"),
                        allTasks.getString("name"),
                        allTasks.getString("description"),
                        allTasks.getString("creation_date"),
                        allTasks.getString("due_date"),
                        allTasks.getInt("completion")));
            }
            return taskItems;
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return taskItems;
        }
    }
}