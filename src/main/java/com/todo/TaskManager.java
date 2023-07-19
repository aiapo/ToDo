package com.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskManager {
    Database tDB;

    ObservableList<Task> taskItems = FXCollections.observableArrayList();

    // Constructor to initialize database
    public TaskManager(Database DB) {
        tDB=DB;
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
        Integer iID = findIndex(taskItems,id);

        return new Task(taskItems.get(iID).id,
                taskItems.get(iID).name,
                taskItems.get(iID).description,
                taskItems.get(iID).creation,
                taskItems.get(iID).due,
                taskItems.get(iID).completion);
    }

    // Update a task's details
    public ObservableList<Task> update(Integer id, String name, String description, String creation, String due, Integer complete){
        // update task in database
        String[] task = {"name", "description", "creation_date", "due_date", "completion"};
        if(tDB.update("Tasks",task,"id = "+id,new Object[]{name,description,creation,due,complete})){
            System.out.println("Task "+name+" updated!");
        }else{
            System.out.println("Error updating "+name+"!");
        }

        taskItems.set(findIndex(taskItems,id),new Task(id,name,description,creation,due,complete));

        // return the task list
        return taskItems;
    }

    // Delete a task
    public ObservableList<Task> delete(Integer id){
        // delete task in database
        String taskName = retrieve(id).name;
        if(tDB.delete("Tasks","id = ?",new Object[]{id})){
            System.out.println("Task "+taskName+" deleted!");
        }else{
            System.out.println("Error deleting "+taskName+"!");
        }

        // return the task list
        taskItems.clear();
        return populateArray();
    }

    // Find the index in the array based on the id in the database
    private Integer findIndex(ObservableList<Task> list,Integer sqlId){
        for(int i=0; i<=list.size(); i++)
            if(list.get(i).id==sqlId)
                return i;
        return -1;
    }

    // Populate the array from the database (ie. on start)
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