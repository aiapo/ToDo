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

                if(tDB.insert("TaskCategory",new Object[]{null,id,0})){
                    System.out.println("Task "+name+" linked!");
                }else{
                    System.out.println("Error linking "+name+"!");
                }
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
        try{
            ResultSet item = tDB.select("Tasks", new String[]{"*"},"id = ?",new Object[]{id});
            return new Task(item.getInt("ID"),
                    item.getString("name"),
                    item.getString("description"),
                    item.getString("creation_date"),
                    item.getString("due_date"),
                    item.getInt("completion"));
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return null;
        }
    }

    // Update a task's details
    public ObservableList<Task> update(Integer id, String name, String description, String creation, String due, Integer complete){
        // update task in database
        String[] task = {"name", "description", "creation_date", "due_date", "completion"};
        if(tDB.update("Tasks",task,"id = "+id,new Object[]{name,description,creation,due,complete})){

        }else{
            System.out.println("Error updating "+name+"!");
        }

        taskItems.set(findIndex(taskItems,id),new Task(id,name,description,creation,due,complete));

        // return the task list
        return taskItems;
    }

    // just update completion
    public ObservableList<Task> update(Integer id, Integer complete){
        return update(id,retrieve(id).name,retrieve(id).description,retrieve(id).creation,retrieve(id).due,complete);
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
        if (list.size()!=0)
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

    public ObservableList<Task> CategoryTasks(Integer id){
        if(id==0){
            taskItems.clear();
            return populateArray();
        }else{
            try{
                taskItems.clear();
                ResultSet allTasks = tDB.select("TaskCategory", new String[]{"*"},"category_id = ?",new Object[]{id});
                while(allTasks.next()) {
                    taskItems.add(retrieve(allTasks.getInt("task_id")));
                }
                return taskItems;
            } catch (SQLException error) {
                System.out.println("Error: "+error.getMessage());
                return taskItems;
            }
        }
    }

    public Integer taskCategory(Integer id){
        try{
            return tDB.select("TaskCategory", new String[]{"category_id"},"task_id = ?",new Object[]{id}).getInt("category_id");
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return 0;
        }
    }

    public Boolean addToCategory(Integer tId,Integer cId) {
        if(tDB.update("TaskCategory",new String[]{"category_id"},"task_id = "+tId,new Object[]{cId})){
            return true;
        }else{
            System.out.println("Error linking "+retrieve(tId).name+"!"+cId);
            return false;
        }
    }

}