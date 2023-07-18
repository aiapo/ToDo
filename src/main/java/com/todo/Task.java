package com.todo;

public class Task {
    public Integer id,completion;
    public String name,description,creation,due;

    Task(Integer nID, String nName, String nDescription, String nCreation, String nDue, Integer nCompletion){
        id = nID;
        name = nName;
        description = nDescription;
        creation = nCreation;
        due = nDue;
        completion = nCompletion;
    }
    @Override public String toString() { return name; }
}
