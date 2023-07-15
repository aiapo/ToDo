package com.todo;

import java.util.Scanner;

public class TaskManager {
    protected static TaskDatabase tDB;

    public TaskManager(){
        tDB.init();
    }

    public static void add(String name, String description, String creation, String due, Integer complete){
        Object[] newTask = {0, name, description, creation, due, complete};
        if(tDB.insert("Tasks",newTask)){
            System.out.println("Task "+name+" created!");
        }else{
            System.out.println("Error creating "+name+"!");
        }

    }

}
