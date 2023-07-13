package com.todo;

import java.io.File;

public class TaskDatabase {
    static String databaseName = "tasks.db";

    static Database toDoDB = new Database(databaseName);
    public static boolean initTaskDB(){
        String tableCreationSQL =
                "CREATE TABLE TASKS " +
                        "(ID             INTEGER     PRIMARY KEY    AUTOINCREMENT," +
                        " NAME           TEXT    NOT NULL, " +
                        " CREATION_DATE  TEXT    NOT NULL, " +
                        " DUE_DATE       TEXT    NOT NULL," +
                        " COMPLETION     INTEGER    NOT NULL)";
        if (new File(databaseName).length() <= 0) {
            if(!toDoDB.createDB())
                return false;
            if(!toDoDB.createDBTables(tableCreationSQL))
                return false;
        }
        return true;
    }

    public static boolean insertTaskDB(String insertName, String createDate, String dueDate, Integer completeStatus){
        String insertTaskSQL = "INSERT INTO TASKS (NAME,CREATION_DATE,DUE_DATE,COMPLETION) " +
                "VALUES ('"+insertName+"','"+createDate+"','"+dueDate+"',"+completeStatus+");";
        return toDoDB.insertDB(insertTaskSQL);
    }
}