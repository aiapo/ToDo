package com.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryManager {
    Database tDB;
    ObservableList<Category> categoryItems = FXCollections.observableArrayList();

    public CategoryManager(Database DB) {
        tDB=DB;
    }

    // Add a category
    // Adds to both a local array (based on Category class) and the DB
    // Returns the Category array
    public ObservableList<Category> add(Integer id, String name, String description) {
        try{
            // add category to database
            if(id == null){
                Object[] newCategory = {null, name, description};
                if(tDB.insert("Categories",newCategory)){
                    System.out.println("Category "+name+" created!");
                }else{
                    System.out.println("Error creating "+name+"!");
                }
                id = tDB.select("Categories", new String[]{"ID"},"MAX",null).getInt("max_id");
            }

            // add category to local list array
            categoryItems.add(new Category(id,name,description));

            // return the category list
            return categoryItems;
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return categoryItems;
        }
    }

    // Retrieve a task as a Category type
    public Category retrieve(Integer id){
        Integer iID = findIndex(categoryItems,id);

        return new Category(categoryItems.get(iID).id,
                categoryItems.get(iID).name,
                categoryItems.get(iID).description);
    }

    // Update a category's details
    public ObservableList<Category> update(Integer id, String name, String description){
        // update category in database
        String[] task = {"name", "description"};
        if(tDB.update("Categories",task,"id = "+id,new Object[]{name,description})){
            System.out.println("Task "+name+" updated!");
        }else{
            System.out.println("Error updating "+name+"!");
        }

        categoryItems.set(findIndex(categoryItems,id),new Category(id,name,description));

        // return the category list
        return categoryItems;
    }

    // Delete a category
    public ObservableList<Category> delete(Integer id){
        // delete category in database
        String categoryName = retrieve(id).name;
        if(tDB.delete("Categories","id = ?",new Object[]{id})){
            System.out.println("Category "+categoryName+" deleted!");
        }else{
            System.out.println("Error deleting "+categoryName+"!");
        }

        // return the category list
        categoryItems.clear();
        return populateArray();
    }

    // Find the index in the array based on the id in the database
    private Integer findIndex(ObservableList<Category> list,Integer sqlId){
        for(int i=0; i<=list.size(); i++)
            if(list.get(i).id==sqlId)
                return i;
        return -1;
    }

    // Populate the array from the database (ie. on start)
    public ObservableList<Category> populateArray(){
        try{
            ResultSet allTasks = tDB.select("Categories", new String[]{"*"});
            while(allTasks.next()) {
                categoryItems.add(new Category(allTasks.getInt("ID"),
                        allTasks.getString("name"),
                        allTasks.getString("description")));
            }
            return categoryItems;
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return categoryItems;
        }
    }

}
