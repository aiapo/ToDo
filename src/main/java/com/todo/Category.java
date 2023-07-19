package com.todo;

public class Category {
    public Integer id;
    public String name,description;

    // This constructor just initializes the Category type
    Category(Integer nID, String nName, String nDescription){
        id = nID;
        name = nName;
        description = nDescription;
    }

    // Overload to just stringify the name (so JavaFX can get the name)
    @Override public String toString() { return name; }
}