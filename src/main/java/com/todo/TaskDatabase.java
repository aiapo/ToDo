package com.todo;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDatabase {
    // For this project we are using SQLite, just call our database file tasks.db
    static String databaseName = "tasks.db";

    // Create the database/connect to it
    static Database database;
    static {
        try {
            database = new Database("tasks.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Initialize the database if it hasn't been already (create tables)
    public static void init(){
        if (new File(databaseName).length() <= 0) {
                String newTable = "Tasks";
                Object[] newAttributes = {
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT",
                        "NAME   TEXT    NOT NULL",
                        "DESCRIPTION    TEXT    NOT NULL",
                        "CREATION_DATE  TEXT    NOT NULL",
                        "DUE_DATE   TEXT    NOT NULL",
                        "COMPLETION INTEGER NOT NULL"
                };
                if(createTable(newTable,newAttributes))
                    System.out.println("Table "+newTable+" created!");
        }
    }

    // Table creator
    private static boolean createTable(String tableName,Object[] attributes){
        try{
            int response = database.create(tableName, attributes);
            if(response==0)
                return true;
            else
                return false;
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return false;
        }
    }

    public static boolean insert(String tableName, Object[] params){
        try{
            int response = database.insert(tableName, params);
            if(response==1)
                return true;
            else
                return false;
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return false;
        }
    }

    public static boolean update(String tableName, String[] attribute, String condition, Object[] params){
        try{
            int response = database.update(tableName,attribute,condition,params);
            if(response==1)
                return true;
            else
                return false;
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return false;
        }
    }

    public static boolean delete(String tableName, String requirement, Object[] param){
        try{
            int response = database.delete(tableName,requirement,param);
            if(response==1)
                return true;
            else
                return false;
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
            return false;
        }
    }

    public ResultSet select(String table, Object[] columns, Object[] params){
        try{
            return database.select(table,columns,params);
        } catch (SQLException error) {
            System.out.println("Error: "+error.getMessage());
        }
        return null;
    }

}