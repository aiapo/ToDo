package com.todo;

// In order to store the tasks for easier manipulations in JavaFX, we store all attributes in an array of Tasks
public class Task {
    public Integer id,completion;
    public String name,description,creation,due;

    // This constructor just initializes the Task type
    Task(Integer nID, String nName, String nDescription, String nCreation, String nDue, Integer nCompletion){
        id = nID;
        name = nName;
        description = nDescription;
        creation = nCreation;
        due = nDue;
        completion = nCompletion;
    }

    // Overload to just stringify the name (so JavaFX can get the name)
    @Override public String toString() { return name; }

    public Boolean isCompleted(){ if(completion==1) return true; else return false; }
}
